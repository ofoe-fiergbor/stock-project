package com.example.stockui;



import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class StageInitializer implements ApplicationListener<ChartApplication.StageReadyEvent> {

    @Value(("classpath:/chart.fxml"))
    private Resource CharResource;
    private String applicationTitle;
    private ApplicationContext applicationContext;

    public StageInitializer(@Value("${string.application.ui.title}") String applicationTitle, ApplicationContext applicationContext) {
        this.applicationTitle = applicationTitle;
        this.applicationContext = applicationContext;
    }

    @Override
    public void onApplicationEvent(ChartApplication.StageReadyEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(CharResource.getURL());
            fxmlLoader.setControllerFactory(aClass -> applicationContext.getBean(aClass));
            Parent parent = fxmlLoader.load();
            Stage stage = event.getStage();
            

            stage.setScene(new Scene(parent, 800, 600));
            stage.setTitle(applicationTitle);
        } catch (IOException e) {
           throw new RuntimeException();
        }
    }
}
