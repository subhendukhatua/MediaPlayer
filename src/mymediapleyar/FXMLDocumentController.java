
package mymediapleyar;

import java.io.File;
import java.net.URL;
import java.time.*;
import java.util.ResourceBundle;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.util.Duration;

/**
 *
 * @author Subhe
 */
public class FXMLDocumentController implements Initializable {
    
    private String path;
    private MediaPlayer mediaPlayer;
    
    @FXML
    private MediaView mediaView;
    
    @FXML
    private Slider progressBar;
    
    @FXML
    private Slider volumeSlider;
    
    public void chooseFileMethod(ActionEvent event){
        
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(null);
        path = file.toURI().toString();
        
        
        if(path!= null){
            Media media = new Media(path);
            mediaPlayer = new MediaPlayer(media);
            mediaView.setMediaPlayer(mediaPlayer);
            
            DoubleProperty widthProp = mediaView.fitWidthProperty();
            DoubleProperty heightProp = mediaView.fitHeightProperty();
            
            widthProp.bind(Bindings.selectDouble(mediaView.sceneProperty(), "width"));
            heightProp.bind(Bindings.selectDouble(mediaView.sceneProperty(), "height"));
            
            mediaPlayer.currentTimeProperty().addListener(new ChangeListener<javafx.util.Duration> () {
                    
                @Override
                public void changed(ObservableValue<? extends javafx.util.Duration> observable, javafx.util.Duration oldValue, javafx.util.Duration newValue) {
                    progressBar.setValue(newValue.toSeconds());
                }
                    });
            
            progressBar.setOnMousePressed(new EventHandler<Event>(){
                    
                @Override
                public void handle(Event event) {
                    mediaPlayer.seek(Duration.seconds(progressBar.getValue()));
                }
            });
            
            
            progressBar.setOnMouseDragged(new EventHandler<Event>(){
                    
                @Override
                public void handle(Event event) {
                    mediaPlayer.seek(Duration.seconds(progressBar.getValue()));
                }
            });
            
            mediaPlayer.setOnReady(new Runnable() {
                @Override
                public void run() {
                    Duration total = media.getDuration();
                    progressBar.setMax(total.toSeconds());
                }
            });
            
            volumeSlider.setValue(mediaPlayer.getVolume()*100);
            volumeSlider.valueProperty().addListener(new InvalidationListener() {
                @Override
                public void invalidated(Observable observable) {
                    mediaPlayer.setVolume(volumeSlider.getValue()/100);
                }
            });
            
            
            
            
           
                
            
            
            
            
            
            mediaPlayer.play();
        }    
        
    }
    
    public void Play(ActionEvent event){
        mediaPlayer.play();
        mediaPlayer.setRate(1);
    }
    
    public void Pause(ActionEvent event){
        mediaPlayer.pause();
    }
    
    public void stop(ActionEvent event){
        mediaPlayer.stop();
    }
    
    public void slowRate(ActionEvent event){
        mediaPlayer.setRate(0.5);
        
    }
    
    public void fastForword(ActionEvent event){
        mediaPlayer.setRate(2);
    }
     
   
    
    
    
    
    
  
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
