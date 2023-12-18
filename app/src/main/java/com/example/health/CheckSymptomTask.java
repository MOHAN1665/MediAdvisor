//package com.example.health;
//
//import android.content.Context;
//import android.os.AsyncTask;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.IOException;
//import java.io.OutputStream;
//import java.lang.ref.WeakReference;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.nio.charset.StandardCharsets;
//
//class CheckSymptomTask extends AsyncTask<String, Void, JSONObject> {
//    private final WeakReference<TextView> causesTextView;
//    private final WeakReference<TextView> severityTextView;
//    private final WeakReference<TextView> recommendationTextView;
//    private final WeakReference<Context> contextReference;
//    private final WeakReference<SymptomViewModel> symptomViewModelReference;
//
//    public CheckSymptomTask(
//            TextView causesTextView,
//            TextView severityTextView,
//            TextView recommendationTextView,
//            Context context,
//            SymptomViewModel symptomViewModel
//    ) {
//        this.causesTextView = new WeakReference<>(causesTextView);
//        this.severityTextView = new WeakReference<>(severityTextView);
//        this.recommendationTextView = new WeakReference<>(recommendationTextView);
//        this.contextReference = new WeakReference<>(context);
//        this.symptomViewModelReference = new WeakReference<>(symptomViewModel);
//    }
//
//    @Override
//    protected JSONObject doInBackground(String... params) {
//        if (params.length == 0) {
//            return null;
//        }
//
//        final String symptom = params[0];
//
//        Context context = contextReference.get();
//        SymptomViewModel symptomViewModel = symptomViewModelReference.get();
//
//        if (context != null && symptomViewModel != null) {
//            // You can use your SymptomViewModel to check the symptom and get the information.
//            symptomViewModel.checkSymptom(symptom);
//
//            // You can get the information from the SymptomViewModel here.
//            String causes = symptomViewModel.getCauses().getValue();
//            String severity = symptomViewModel.getSeverity().getValue();
//            String recommendation = symptomViewModel.getRecommendation().getValue();
//
//            try {
//                URL url = new URL("http://192.168.1.28:3000"); // Replace with your server URL
//                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//                connection.setRequestMethod("POST");
//                connection.setRequestProperty("Content-Type", "application/json");
//                connection.setDoOutput(true);
//
//                JSONObject postData = new JSONObject();
//                postData.put("symptom", symptom);
//                postData.put("symptoms", causes);
//                postData.put("causes", causes);
//                postData.put("severity", severity);
//                postData.put("recommendation", recommendation);
//
//                try (OutputStream os = connection.getOutputStream()) {
//                    byte[] input = postData.toString().getBytes(StandardCharsets.UTF_8);
//                    os.write(input, 0, input.length);
//                }
//
//                int responseCode = connection.getResponseCode();
//                if (responseCode != HttpURLConnection.HTTP_OK) {
//                    // Handle the error response
//                    // You can also log or display an error message
//                    // In this example, a Toast message is displayed
//                    Toast.makeText(context, "Error communicating with the server", Toast.LENGTH_SHORT).show();
//                    return null;
//                }
//            } catch (IOException | JSONException e) {
//                e.printStackTrace();
//            }
//        }
//
//        return null;
//    }
//
//    protected void onPostExecute(JSONObject result) {
//        // Retrieve UI components
//        TextView causesView = causesTextView.get();
//        TextView severityView = severityTextView.get();
//        TextView recommendationView = recommendationTextView.get();
//        Context context = contextReference.get();
//
//        if (causesView != null && severityView != null && recommendationView != null) {
//            if (result != null) {
//                // Retrieve causes, severity, and recommendation from the JSON result
//                String causes = result.optString("causes", context.getString(R.string.causes_not_found));
//                String severity = result.optString("severity", context.getString(R.string.severity_not_available));
//                String recommendation = result.optString("recommendation", context.getString(R.string.recommendation_default));
//
//                // Update the UI with the retrieved values
//                causesView.setText(context.getString(R.string.causes_template, causes));
//                severityView.setText(context.getString(R.string.severity_template, severity));
//                recommendationView.setText(context.getString(R.string.recommendation_template, recommendation));
//            } else {
//                // Handle the case where there was an error or result is null
//                causesView.setText(context.getString(R.string.causes_not_found));
//                severityView.setText(context.getString(R.string.severity_not_available));
//                recommendationView.setText(context.getString(R.string.recommendation_default));
//
//                // Display an error message using a Toast
//                Toast.makeText(context, context.getString(R.string.error_communicating_with_server), Toast.LENGTH_SHORT).show();
//
//            }
//        }
//    }
//}
