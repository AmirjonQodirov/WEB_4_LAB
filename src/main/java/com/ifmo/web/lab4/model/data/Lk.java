package com.ifmo.web.lab4.model.data;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.ValueRange;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

@Component
public class Lk {
    private static Sheets sheetsService;
    private static String APPLICATION_NAME = "WEB_4";
    //    private static String SPREADSHEET_ID = "1hGx52Q6omjFo7D_0L_FXXhb5FyVR2I6m2bmbOKYdEVA";
    private static String SPREADSHEET_ID = "1HTRtghv20awgF9zEJ_D-0_QKoy1wUiJP1q3vIie5fkM";

    private static Credential authorise() throws Exception{
        InputStream in = Lk.class.getResourceAsStream("/authorise.json");
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(
                JacksonFactory.getDefaultInstance(),new InputStreamReader(in)
        );
        List<String> scopes = Arrays.asList(SheetsScopes.SPREADSHEETS);
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),JacksonFactory.getDefaultInstance(),clientSecrets,scopes)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File("tokens")))
                .setAccessType("offline")
                .build();
        Credential credential = new AuthorizationCodeInstalledApp(
                flow,new LocalServerReceiver())
                .authorize("user");
        return credential;
    }

    public static Sheets getSheetsService() throws Exception {
        Credential credential = authorise();
        return new Sheets.Builder(GoogleNetHttpTransport.newTrustedTransport(),
                JacksonFactory.getDefaultInstance(),credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    public String getLKAmir() throws Exception {
        sheetsService = getSheetsService();
//        String range = "P3210!Z7";
        String range = "E1";
        ValueRange response = sheetsService.spreadsheets().values().get(SPREADSHEET_ID,range)
                .execute();
        List<List<Object>> values = response.getValues();

        return (String) values.get(0).get(0);
    }

    public String getLKBeha() throws Exception {
        sheetsService = getSheetsService();
//        String range = "P3210!Z7";
        String range = "E2";
        ValueRange response = sheetsService.spreadsheets().values().get(SPREADSHEET_ID,range)
                .execute();
        List<List<Object>> values = response.getValues();

        return (String) values.get(0).get(0);
    }



}
