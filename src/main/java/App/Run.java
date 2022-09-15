package App;


import java.io.IOException;

public class Run {
    static ElectionParticipationData epd;

    static {
        try {
            epd = new ElectionParticipationData();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        //System.out.println(epd.kommuner.get(0));
    }
}
