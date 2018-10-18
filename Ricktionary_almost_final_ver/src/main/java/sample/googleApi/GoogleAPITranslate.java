package sample.googleApi;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.Translate.TranslateOption;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;
import com.jfoenix.controls.JFXTextArea;

public class GoogleAPITranslate extends Thread {
    String[] textToBeTranslated;
    String translatedText;
    JFXTextArea textArea;

    public GoogleAPITranslate(String[] whatever, JFXTextArea textArea) {
        this.textToBeTranslated = whatever;
        this.textArea = textArea;
    }

    public static String translates(String text, String sourceLanguage, String targetLanguage) {
        // Instantiates a client
        Translate translate = TranslateOptions.getDefaultInstance().getService();

            // Translates some text into Russian
            Translation translation =
                    translate.translate(
                            text,
                            TranslateOption.sourceLanguage(sourceLanguage),
                            TranslateOption.targetLanguage(targetLanguage));

//        System.out.println(translation.getTranslatedText());
            return translation.getTranslatedText();
    }

    public void run() {
        try {
            String output = "";
            textArea.setText("Loading...");
            for (String line : this.textToBeTranslated) {
                output += (translates(line, "en", "vi") + "\n");
            }
            textArea.setText(output);
//            this.translatedText = translates(this.textToBeTranslated, "en", "vi");
        } catch (Exception e) {
            System.out.println("Exception is caught");
        }
    }


//    public static void main(String[] args) {
//        System.out.println(translates("Thang is gay, Ninh is awesome, Thang has no girlfriend. Thang has tiny penis","en","vi"));
//    }
}
