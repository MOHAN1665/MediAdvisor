package com.example.health;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class SymptomViewModel extends AndroidViewModel {
    private final MutableLiveData<String> causes = new MutableLiveData<>();
    private final MutableLiveData<String> severity = new MutableLiveData<>();
    private final MutableLiveData<String> recommendation = new MutableLiveData<>();

    public SymptomViewModel(Application application) {
        super(application);
    }

    public LiveData<String> getCauses() {
        return causes;
    }

    public LiveData<String> getSeverity() {
        return severity;
    }

    public LiveData<String> getRecommendation() {
        return recommendation;
    }

    public void checkSymptom(String symptom) {

        Log.d("SymptomViewModel", "Checking symptom: " + symptom);

        // Sample symptom-checking logic
        switch (symptom.toLowerCase()) {
            case "fever":
                causes.setValue("Common Cold");
                severity.setValue("Mild");
                recommendation.setValue("Keep child hydrated, provide rest and monitor temperature");
                break;
            case "cold":
                causes.setValue("Viral Infection");
                severity.setValue("Mild");
                recommendation.setValue("Rest, drink fluids, and avoid cold items");
                break;
            case "headache":
                causes.setValue("Various causes, consult a doctor for specific diagnosis");
                severity.setValue("Varies");
                recommendation.setValue("Identify the cause, take pain relievers if needed");
                break;
            case "cough":
                causes.setValue("Respiratory Infection");
                severity.setValue("Mild to Severe");
                recommendation.setValue("Rest, drink fluids, and consult a doctor if severe");
                break;
            case "skin infection":
                causes.setValue("Insect bites, bacterial infection, fungal infection");
                severity.setValue("Mild");
                recommendation.setValue("Maintain hygiene, consult a specialist if severity is high.");
                break;
            case "food poisoning":
                causes.setValue("Excess junk food, Unhygienic practices");
                severity.setValue("Mild");
                recommendation.setValue("Take light food, wash hands regularly, avoid junk food, take prescribed medications.");
                break;
            case "corona":
                causes.setValue("Viral spreading through contact or air");
                severity.setValue("High");
                recommendation.setValue("Keep child hydrated, provide rest, monitor temperature, isolate until recovery, have protein food, sanitize hands regularly.");
                break;
            case "physical injury":
                causes.setValue("Falling down, playing with sharp items");
                severity.setValue("High");
                recommendation.setValue("Treat the wound properly, avoid more movement of injured joint, take rest.");
                break;
            case "dengue":
                causes.setValue("Mosquito bite");
                severity.setValue("High");
                recommendation.setValue("Monitor temperature, monitor platelet count, consult a specialist, take rest.");
                break;
            case "typhoid":
                causes.setValue("Drinking polluted water");
                severity.setValue("High");
                recommendation.setValue("Monitor temperature, take nutritious food, consult a specialist, take rest.");
                break;
            case "malaria":
                causes.setValue("Mosquito bite");
                severity.setValue("High");
                recommendation.setValue("Monitor temperature, take nutritious food, consult a specialist, take rest.");
                break;
            default:
                causes.setValue("Not Found");
                severity.setValue("N/A");
                recommendation.setValue("Please consult a healthcare professional");
                break;
        }
    }
}
