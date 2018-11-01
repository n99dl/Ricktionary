package sample.googleApi;

import com.google.cloud.texttospeech.v1.AudioConfig;
import com.google.cloud.texttospeech.v1.AudioEncoding;
import com.google.cloud.texttospeech.v1.SsmlVoiceGender;
import com.google.cloud.texttospeech.v1.SynthesisInput;
import com.google.cloud.texttospeech.v1.SynthesizeSpeechResponse;
import com.google.cloud.texttospeech.v1.TextToSpeechClient;
import com.google.cloud.texttospeech.v1.VoiceSelectionParams;
import com.google.protobuf.ByteString;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class GoogleAPITextToSpeech extends Thread {
    String textToSpeech;

    public GoogleAPITextToSpeech(String textToSpeech){
        this.textToSpeech = textToSpeech;
    }

    public static void outputToMp3(String text){
        try (TextToSpeechClient textToSpeechClient = TextToSpeechClient.create()) {
            // Set the text input to be synthesized
            SynthesisInput input = SynthesisInput.newBuilder()
                    .setText(text)
                    .build();

            // Build the voice request, select the language code ("en-US") and the ssml voice gender
            // ("neutral")
            VoiceSelectionParams voice = VoiceSelectionParams.newBuilder()
                    .setLanguageCode("en")
                    .setSsmlGender(SsmlVoiceGender.FEMALE)
                    .build();

            // Select the type of audio file you want returned
            AudioConfig audioConfig = AudioConfig.newBuilder()
                    .setAudioEncoding(AudioEncoding.MP3)
                    .build();

            // Perform the text-to-speech request on the text input with the selected voice parameters and
            // audio file type
            SynthesizeSpeechResponse response = textToSpeechClient.synthesizeSpeech(input, voice,
                    audioConfig);

            // Get the audio contents from the response
            ByteString audioContents = response.getAudioContent();

            // Write the response to the output file    .
            try (OutputStream out = new FileOutputStream("src/main/resources/audio/output.mp3")) {
//            try (OutputStream out = new FileOutputStream("output.mp3")) {
                out.write(audioContents.toByteArray());
//                System.out.println("Audio content written to file \"output.mp3\"");
                File f = new File("src/main/resources/audio/output.mp3");
//              File f = new File("output.mp3");
                Media media = new Media(f.toURI().toURL().toExternalForm());
                MediaPlayer mp = new MediaPlayer(media);
                mp.play();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run()
    {
        try
        {
            outputToMp3(textToSpeech);
        }
        catch (Exception e)
        {
            System.out.println ("Exception is caught");
        }
    }
//    public static void main(String[] args) {
//        GoogleAPITextToSpeech.outputToMp3("Testing word. utopiosphere");
//    }
}
