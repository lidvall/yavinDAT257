package App;


public class Run {
    static QueryTabels queryTabels;


    public static void main(String[] args) throws Exception {
        String url = "https://api.scb.se/OV0104/v1/doris/sv/ssd/START/ME/ME0104/ME0104D/ME0104T4";
        String requestPath = "src/main/java/App/ElectionParticipation.json";
        QueryTabels queryTabels = new QueryTabels(url,requestPath);

    }




}


