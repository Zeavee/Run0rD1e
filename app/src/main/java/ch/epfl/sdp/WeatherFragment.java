package ch.epfl.sdp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.androdocs.httprequest.HttpRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import ch.epfl.sdp.entity.PlayerManager;
import ch.epfl.sdp.geometry.GeoPoint;

public class WeatherFragment extends Fragment {
    private TextView addressTxt, updated_atTxt, statusTxt, tempTxt, temp_minTxt, temp_maxTxt, sunriseTxt,
            sunsetTxt, windTxt, humidityTxt;

    private long sunrise, sunset;
    private String updatedAtText, temp, tempMin,  tempMax, humidity, windSpeed, weatherDescription, address;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_weather, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        addressTxt = getView().findViewById(R.id.address);
        updated_atTxt = getView().findViewById(R.id.updated_at);
        statusTxt = getView().findViewById(R.id.status);
        tempTxt = getView().findViewById(R.id.temp);
        temp_minTxt = getView().findViewById(R.id.temp_min);
        temp_maxTxt = getView().findViewById(R.id.temp_max);
        sunriseTxt = getView().findViewById(R.id.sunrise);
        sunsetTxt = getView().findViewById(R.id.sunset);
        windTxt = getView().findViewById(R.id.wind);
        humidityTxt = getView().findViewById(R.id.humidity);

        new WeatherTask().execute();
    }

    class WeatherTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            /* Showing the ProgressBar, Making the main design GONE */
            getView().findViewById(R.id.loader).setVisibility(View.VISIBLE);
            getView().findViewById(R.id.mainContainer).setVisibility(View.GONE);
            getView().findViewById(R.id.errorText).setVisibility(View.GONE);
        }

        protected String doInBackground(String... args) {
            GeoPoint location = PlayerManager.getCurrentUser().getLocation();
            String API = "b840e4c524a759fd39f896b665f92de0";
            return HttpRequest.excuteGet("https://api.openweathermap.org/data/2.5/weather?lat=" + location.getLatitude() + "&lon=" + location.getLongitude() + "&units=metric&appid=" + API);
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                JSONObject jsonObj = new JSONObject(result);
                JSONObject main = jsonObj.getJSONObject("main");
                JSONObject sys = jsonObj.getJSONObject("sys");
                JSONObject wind = jsonObj.getJSONObject("wind");
                JSONObject weather = jsonObj.getJSONArray("weather").getJSONObject(0);

                long updatedAt = jsonObj.getLong("dt");
                updatedAtText = "Updated at: " + new SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(new Date(updatedAt * 1000));
                temp = main.getString("temp") + "°C";
                tempMin = "Min Temp: " + main.getString("temp_min") + "°C";
                tempMax = "Max Temp: " + main.getString("temp_max") + "°C";
                humidity = main.getString("humidity");

                sunrise = sys.getLong("sunrise");
                sunset = sys.getLong("sunset");
                windSpeed = wind.getString("speed");
                weatherDescription = weather.getString("description");

                address = jsonObj.getString("name") + ", " + sys.getString("country");

                putDataInLayout();

                /* Views populated, Hiding the loader, Showing the main design */
                getView().findViewById(R.id.loader).setVisibility(View.GONE);
                getView().findViewById(R.id.mainContainer).setVisibility(View.VISIBLE);

            } catch (JSONException e) {
                getView().findViewById(R.id.loader).setVisibility(View.GONE);
                getView().findViewById(R.id.errorText).setVisibility(View.VISIBLE);
            }
        }

        private void putDataInLayout() {
            addressTxt.setText(address);
            updated_atTxt.setText(updatedAtText);
            statusTxt.setText(weatherDescription.toUpperCase());
            tempTxt.setText(temp);
            temp_minTxt.setText(tempMin);
            temp_maxTxt.setText(tempMax);
            sunriseTxt.setText(new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(new Date(sunrise * 1000)));
            sunsetTxt.setText(new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(new Date(sunset * 1000)));
            windTxt.setText(windSpeed);
            humidityTxt.setText(humidity);
        }
    }
}
