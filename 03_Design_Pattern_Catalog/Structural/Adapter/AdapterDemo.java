// Target interface - what the client expects
interface MediaPlayer {
    void play(String filename);
}

// Adaptee - existing class with incompatible interface
class AdvancedPlayer {

    public void playVLC(String file) {
        System.out.println("VLC: " + file);
    }

    public void playMP4(String file) {
        System.out.println("MP4: " + file);
    }
}

// Adapter - wraps AdvancedPlayer, implements MediaPlayer
class MediaAdapter implements MediaPlayer {

    private AdvancedPlayer adaptee; // composition (Object Adapter)

    public MediaAdapter() {
        this.adaptee = new AdvancedPlayer();
    }

    @Override
    public void play(String filename) { // translates the call

        if (filename.endsWith(".vlc")) {
            adaptee.playVLC(filename); // delegates to adaptee
        } else if (filename.endsWith(".mp4")) {
            adaptee.playMP4(filename); // delegates to adaptee
        } else {
            System.out.println("Unsupported format: " + filename);
        }
    }
}

// Client
public class AdapterDemo {

    public static void main(String[] args) {

        // Client only knows MediaPlayer
        MediaPlayer player = new MediaAdapter();

        player.play("movie.vlc");
        player.play("show.mp4");
        player.play("music.mp3");
    }
}
