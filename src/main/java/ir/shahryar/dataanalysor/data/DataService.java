package ir.shahryar.dataanalysor.data;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import ir.shahryar.dataanalysor.analysis.Analyzer;
import ir.shahryar.dataanalysor.analysis.LinearRegression;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;

@Service
public class DataService {
    private static final String url = "http://localhost:9090/coin/";

    public String linearRegressionAnalyze(String coinName) throws IOException {
        ArrayList<Data> dataList = getData(coinName);
        Analyzer analyzer;
        try {
            analyzer = getLinearRegression(dataList);
        } catch (IllegalArgumentException e) {
            return "no item found for analyze";
        }
        Data lastData = dataList.get(dataList.size() - 1);
        long nextPoint = 2 * lastData.getDate() - dataList.get(dataList.size() - 2).getDate();
        long nextPrice = (long) analyzer.predict(nextPoint);
        int compareResult = Long.compare(nextPrice, lastData.getPrice());
        switch (compareResult) {
            case 0:
                return "No matter";
            case 1:
                return "Buy";
            default:
                return "Sell";
        }
    }

    private LinearRegression getLinearRegression(ArrayList<Data> dataList) {
        double[] x = new double[dataList.size()];
        double[] y = new double[dataList.size()];
        for (int i = 0; i < dataList.size(); i++) {
            Data data = dataList.get(i);
            x[i] = data.getDate();
            y[i] = data.getPrice();
        }
        return new LinearRegression(x, y);
    }

    private ArrayList<Data> getData(String coinName) throws IOException {
        long now = System.currentTimeMillis();
        Request request = new Request.Builder()
                .url(url + coinName + "/" + (now - 7 * 24 * 60 * 60 * 1000) + "/" + now)
                .build();
        Call call = new OkHttpClient.Builder().build().newCall(request);
        Response response = call.execute();
        Type type = new TypeToken<ArrayList<Data>>() {
        }.getType();
        String body = response.body().string();
        ArrayList<Data> result = new Gson().fromJson(body, type);
        Collections.sort(result);
        return result;
    }
}
