package movielibrary.com.android.hossain.movieapplicationfragment.NetworkUtil;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import movielibrary.com.android.hossain.movieapplicationfragment.BuildConfig;

/**
 * Created by hossain on 4/25/18.
 */

public class MovieDBAPI {
    private static final String API_KEY = BuildConfig.MOVIE_API_KEY;
    private static final String API_LINK = "https://api.themoviedb.org/3";
    private static final String POPULAR_MOVIE_STRING = API_LINK+"/movie/popular";
    private static final String POPULAR_TV_STRING = API_LINK+"/tv/popular";
    private static final String TOP_RATED_MOVIE_STRING = API_LINK+"/movie/top_rated";
    private static final String API_IMAGE_URL = "https://image.tmdb.org/t/p/w500";


    public static String getPopularMovieResponse(){
        URL popularURL = buildURL(POPULAR_MOVIE_STRING);
        String response = getURLResponse(popularURL);
        return response;
    }

    public static String getApiImageUrl(String imageLink){
        return API_IMAGE_URL+imageLink;
    }

    public static String getMovieTrailerURL(int movieId){
        return API_LINK+"/movie/"+movieId+"/videos";
    }
    public static String getMovieReviewURL(int movieId){
        return API_LINK+"/movie/"+movieId+"/reviews";
    }
    public static String getTVTrailerURL(int movieId){
        return API_LINK+"/tv/"+movieId+"/videos";
    }
    public static String getTVReviewURL(int movieId){
        return API_LINK+"/tv/"+movieId+"/reviews";
    }

    public static String getPopularTVResponse(){
        return getURLResponse(buildURL(POPULAR_TV_STRING));
    }

    public static String getTopRatedMovieResponse(){
        return getURLResponse(buildURL(TOP_RATED_MOVIE_STRING));
    }

    public static String getMovieTrailerResponse(int movieId){
        URL movieTrailerURL = buildURL(getMovieTrailerURL(movieId));
        return getURLResponse(movieTrailerURL);
    }

    public static String getTVTrailerResponse(int TVId){
        URL TVTrailerURL = buildURL(getTVTrailerURL(TVId));
        return getURLResponse(TVTrailerURL);
    }

    public static String getMovieReviewResponse(int movieId){
        URL movieTrailerURL = buildURL(getMovieReviewURL(movieId));
        return getURLResponse(movieTrailerURL);
    }

    public static String getTVReviewResponse(int TVId){
        URL TVTrailerURL = buildURL(getTVReviewURL(TVId));
        return getURLResponse(TVTrailerURL);
    }


    //TODO:: Change the name back
    private static String getURLResponse(URL url){
        String response = "";
        try{
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-length", "0");
            connection.setUseCaches(false);
            connection.setAllowUserInteraction(false);
            try {
                connection.connect();
                int status = connection.getResponseCode();
                switch (status){
                    case 200:
                    case 201:
                        InputStream in = connection.getInputStream();
                        Scanner scanner = new Scanner(in);
                        while (scanner.hasNext()) {
                            response = response + scanner.next()+" ";
                        }
                        scanner.close();
                        break;
                    default:
                        response = "Unexpected network Error";
                        break;
                }
            } finally {
                connection.disconnect();
            }
        }
        catch (MalformedURLException e) {}
        catch (IOException e) {}
        Log.d("Response", response);
        return response;
    }

//    private static String getURLResponse(URL url){
//        return "{\n" +
//                "\t\"page\":1,\n" +
//                "\t\"total_results\":19825,\n" +
//                "\t\"total_pages\":992,\n" +
//                "\t\"results\":\n" +
//                "\t[\n" +
//                "\t\t{\n" +
//                "\t\t\"vote_count\":1488,\n" +
//                "\t\t\"id\":337167,\n" +
//                "\t\t\"video\":false,\n" +
//                "\t\t\"vote_average\":6,\n" +
//                "\t\t\"title\":\"Fifty Shades Freed\",\n" +
//                "\t\t\"popularity\":523.855789,\n" +
//                "\t\t\"poster_path\":\"\\/jjPJ4s3DWZZvI4vw8Xfi4Vqa1Q8.jpg\",\n" +
//                "\t\t\"original_language\":\"en\",\n" +
//                "\t\t\"original_title\":\"Fifty Shades Freed\",\n" +
//                "\t\t\"genre_ids\":[\n" +
//                "\t\t\t18,\n" +
//                "\t\t\t10749\n" +
//                "\t\t],\n" +
//                "\t\t\"backdrop_path\":\"\\/9ywA15OAiwjSTvg3cBs9B7kOCBF.jpg\",\n" +
//                "\t\t\"adult\":false,\n" +
//                "\t\t\"overview\":\"Believing they have left behind shadowy figures from their past, newlyweds Christian and Ana fully embrace an inextricable connection and shared life of luxury. But just as she steps into her role as Mrs. Grey and he relaxes into an unfamiliar stability, new threats could jeopardize their happy ending before it even begins.\",\n" +
//                "\t\t\"release_date\":\"2018-02-07\"\n" +
//                "\t\t},\n" +
//                "\t\t{\n" +
//                "\t\t\"vote_count\":7013,\n" +
//                "\t\t\"id\":269149,\"video\":false,\"vote_average\":7.7,\"title\":\"Zootopia\",\"popularity\":280.60555,\"poster_path\":\"\\/sM33SANp9z6rXW8Itn7NnG1GOEs.jpg\",\"original_language\":\"en\",\"original_title\":\"Zootopia\",\"genre_ids\":[16,12,10751,35],\"backdrop_path\":\"\\/mhdeE1yShHTaDbJVdWyTlzFvNkr.jpg\",\"adult\":false,\"overview\":\"Determined to prove herself, Officer Judy Hopps, the first bunny on Zootopia's police force, jumps at the chance to crack her first case - even if it means partnering with scam-artist fox Nick Wilde to solve the mystery.\",\"release_date\":\"2016-02-11\"},{\"vote_count\":274,\"id\":299536,\"video\":false,\"vote_average\":8.6,\"title\":\"Avengers: Infinity War\",\"popularity\":222.346945,\"poster_path\":\"\\/7WsyChQLEftFiDOVTGkv3hFpyyt.jpg\",\"original_language\":\"en\",\"original_title\":\"Avengers: Infinity War\",\"genre_ids\":[12,878,14,28],\"backdrop_path\":\"\\/kbGO5mHPK7rh516MgAIJUQ9RvqD.jpg\",\"adult\":false,\"overview\":\"As the Avengers and their allies have continued to protect the world from threats too large for any one hero to handle, a new danger has emerged from the cosmic shadows: Thanos. A despot of intergalactic infamy, his goal is to collect all six Infinity Stones, artifacts of unimaginable power, and use them to inflict his twisted will on all of reality. Everything the Avengers have fought for has led up to this moment - the fate of Earth and existence itself has never been more uncertain.\",\"release_date\":\"2018-04-25\"},{\"vote_count\":277,\"id\":427641,\"video\":false,\"vote_average\":5.9,\"title\":\"Rampage\",\"popularity\":170.690934,\"poster_path\":\"\\/30oXQKwibh0uANGMs0Sytw3uN22.jpg\",\"original_language\":\"en\",\"original_title\":\"Rampage\",\"genre_ids\":[28,12,878],\"backdrop_path\":\"\\/wrqUiMXttHE4UBFMhLHlN601MZh.jpg\",\"adult\":false,\"overview\":\"Primatologist Davis Okoye shares an unshakable bond with George, the extraordinarily intelligent, silverback gorilla who has been in his care since birth.  But a rogue genetic experiment gone awry mutates this gentle ape into a raging creature of enormous size.  To make matters worse, it’s soon discovered there are other similarly altered animals.  As these newly created alpha predators tear across North America, destroying everything in their path, Okoye teams with a discredited genetic engineer to secure an antidote, fighting his way through an ever-changing battlefield, not only to halt a global catastrophe but to save the fearsome creature that was once his friend.\",\"release_date\":\"2018-04-12\"},{\"vote_count\":4022,\"id\":354912,\"video\":false,\"vote_average\":7.8,\"title\":\"Coco\",\"popularity\":169.175025,\"poster_path\":\"\\/eKi8dIrr8voobbaGzDpe8w0PVbC.jpg\",\"original_language\":\"en\",\"original_title\":\"Coco\",\"genre_ids\":[12,35,10751,16],\"backdrop_path\":\"\\/askg3SMvhqEl4OL52YuvdtY40Yb.jpg\",\"adult\":false,\"overview\":\"Despite his family’s baffling generations-old ban on music, Miguel dreams of becoming an accomplished musician like his idol, Ernesto de la Cruz. Desperate to prove his talent, Miguel finds himself in the stunning and colorful Land of the Dead following a mysterious chain of events. Along the way, he meets charming trickster Hector, and together, they set off on an extraordinary journey to unlock the real story behind Miguel's family history.\",\"release_date\":\"2017-10-27\"},{\"vote_count\":4327,\"id\":284054,\"video\":false,\"vote_average\":7.3,\"title\":\"Black Panther\",\"popularity\":145.745733,\"poster_path\":\"\\/uxzzxijgPIY7slzFvMotPv8wjKA.jpg\",\"original_language\":\"en\",\"original_title\":\"Black Panther\",\"genre_ids\":[28,12,14,878],\"backdrop_path\":\"\\/b6ZJZHUdMEFECvGiDpJjlfUWela.jpg\",\"adult\":false,\"overview\":\"King T'Challa returns home from America to the reclusive, technologically advanced African nation of Wakanda to serve as his country's new leader. However, T'Challa soon finds that he is challenged for the throne by factions within his own country as well as without.  Using powers reserved to Wakandan kings, T'Challa assumes the Black Panther mantel to join with girlfriend Nakia, the queen-mother, his princess-kid sister,  members of the Dora Milaje (the Wakandan \\\"special forces\\\"), and an American secret agent, to prevent Wakanda from being dragged into a world war.\",\"release_date\":\"2018-02-13\"},{\"vote_count\":7997,\"id\":321612,\"video\":false,\"vote_average\":6.8,\"title\":\"Beauty and the Beast\",\"popularity\":137.612615,\"poster_path\":\"\\/tWqifoYuwLETmmasnGHO7xBjEtt.jpg\",\"original_language\":\"en\",\"original_title\":\"Beauty and the Beast\",\"genre_ids\":[10751,14,10749],\"backdrop_path\":\"\\/6aUWe0GSl69wMTSWWexsorMIvwU.jpg\",\"adult\":false,\"overview\":\"A live-action adaptation of Disney's version of the classic tale of a cursed prince and a beautiful young woman who helps him break the spell.\",\"release_date\":\"2017-03-16\"},{\"vote_count\":5099,\"id\":181808,\"video\":false,\"vote_average\":7.1,\"title\":\"Star Wars: The Last Jedi\",\"popularity\":134.571151,\"poster_path\":\"\\/kOVEVeg59E0wsnXmF9nrh6OmWII.jpg\",\"original_language\":\"en\",\"original_title\":\"Star Wars: The Last Jedi\",\"genre_ids\":[14,12,878],\"backdrop_path\":\"\\/oVdLj5JVqNWGY0LEhBfHUuMrvWJ.jpg\",\"adult\":false,\"overview\":\"Rey develops her newly discovered abilities with the guidance of Luke Skywalker, who is unsettled by the strength of her powers. Meanwhile, the Resistance prepares to do battle with the First Order.\",\"release_date\":\"2017-12-13\"},{\"vote_count\":7653,\"id\":198663,\"video\":false,\"vote_average\":7,\"title\":\"The Maze Runner\",\"popularity\":116.638467,\"poster_path\":\"\\/coss7RgL0NH6g4fC2s5atvf3dFO.jpg\",\"original_language\":\"en\",\"original_title\":\"The Maze Runner\",\"genre_ids\":[28,9648,878,53],\"backdrop_path\":\"\\/lkOZcsXcOLZYeJ2YxJd3vSldvU4.jpg\",\"adult\":false,\"overview\":\"Set in a post-apocalyptic world, young Thomas is deposited in a community of boys after his memory is erased, soon learning they're all trapped in a maze that will require him to join forces with fellow “runners” for a shot at escape.\",\"release_date\":\"2014-09-10\"},{\"vote_count\":12491,\"id\":118340,\"video\":false,\"vote_average\":7.9,\"title\":\"Guardians of the Galaxy\",\"popularity\":111.7277,\"poster_path\":\"\\/y31QB9kn3XSudA15tV7UWQ9XLuW.jpg\",\"original_language\":\"en\",\"original_title\":\"Guardians of the Galaxy\",\"genre_ids\":[28,878,12],\"backdrop_path\":\"\\/bHarw8xrmQeqf3t8HpuMY7zoK4x.jpg\",\"adult\":false,\"overview\":\"Light years from Earth, 26 years after being abducted, Peter Quill finds himself the prime target of a manhunt after discovering an orb wanted by Ronan the Accuser.\",\"release_date\":\"2014-07-30\"},{\"vote_count\":722,\"id\":447332,\"video\":false,\"vote_average\":7.4,\"title\":\"A Quiet Place\",\"popularity\":110.996309,\"poster_path\":\"\\/nAU74GmpUk7t5iklEp3bufwDq4n.jpg\",\"original_language\":\"en\",\"original_title\":\"A Quiet Place\",\"genre_ids\":[18,27,53,878],\"backdrop_path\":\"\\/roYyPiQDQKmIKUEhO912693tSja.jpg\",\"adult\":false,\"overview\":\"A family is forced to live in silence while hiding from creatures that hunt by sound.\",\"release_date\":\"2018-04-05\"},{\"vote_count\":5678,\"id\":284053,\"video\":false,\"vote_average\":7.4,\"title\":\"Thor: Ragnarok\",\"popularity\":105.694094,\"poster_path\":\"\\/rzRwTcFvttcN1ZpX2xv4j3tSdJu.jpg\",\"original_language\":\"en\",\"original_title\":\"Thor: Ragnarok\",\"genre_ids\":[28,12,14],\"backdrop_path\":\"\\/kaIfm5ryEOwYg8mLbq8HkPuM1Fo.jpg\",\"adult\":false,\"overview\":\"Thor is imprisoned on the other side of the universe and finds himself in a race against time to get back to Asgard to stop Ragnarok, the prophecy of destruction to his homeworld and the end of Asgardian civilization, at the hands of an all-powerful new threat, the ruthless Hela.\",\"release_date\":\"2017-10-25\"},{\"vote_count\":14360,\"id\":24428,\"video\":false,\"vote_average\":7.5,\"title\":\"The Avengers\",\"popularity\":100.338592,\"poster_path\":\"\\/cezWGskPY5x7GaglTTRN4Fugfb8.jpg\",\"original_language\":\"en\",\"original_title\":\"The Avengers\",\"genre_ids\":[878,28,12],\"backdrop_path\":\"\\/hbn46fQaRmlpBuUrEiFqv0GDL6Y.jpg\",\"adult\":false,\"overview\":\"When an unexpected enemy emerges and threatens global safety and security, Nick Fury, director of the international peacekeeping agency known as S.H.I.E.L.D., finds himself in need of a team to pull the world back from the brink of disaster. Spanning the globe, a daring recruitment effort begins!\",\"release_date\":\"2012-04-25\"},{\"vote_count\":5514,\"id\":119450,\"video\":false,\"vote_average\":7.2,\"title\":\"Dawn of the Planet of the Apes\",\"popularity\":99.87901,\"poster_path\":\"\\/2EUAUIu5lHFlkj5FRryohlH6CRO.jpg\",\"original_language\":\"en\",\"original_title\":\"Dawn of the Planet of the Apes\",\"genre_ids\":[878,28,18,53],\"backdrop_path\":\"\\/t7VSsAbIQS6kpO4ikuCNSiugsSy.jpg\",\"adult\":false,\"overview\":\"A group of scientists in San Francisco struggle to stay alive in the aftermath of a plague that is wiping out humanity, while Caesar tries to maintain dominance over his community of intelligent apes.\",\"release_date\":\"2014-06-26\"},{\"vote_count\":1244,\"id\":274855,\"video\":false,\"vote_average\":5.7,\"title\":\"Geostorm\",\"popularity\":95.014198,\"poster_path\":\"\\/nrsx0jEaBgXq4PWo7SooSnYJTv.jpg\",\"original_language\":\"en\",\"original_title\":\"Geostorm\",\"genre_ids\":[28,878,53],\"backdrop_path\":\"\\/79X8JkGxzc1tJMi0KxUSaPLooVg.jpg\",\"adult\":false,\"overview\":\"After an unprecedented series of natural disasters threatened the planet, the world's leaders came together to create an intricate network of satellites to control the global climate and keep everyone safe. But now, something has gone wrong: the system built to protect Earth is attacking it, and it becomes a race against the clock to uncover the real threat before a worldwide geostorm wipes out everything and everyone along with it.\",\"release_date\":\"2017-10-13\"},{\"vote_count\":1419,\"id\":336843,\"video\":false,\"vote_average\":7.2,\"title\":\"Maze Runner: The Death Cure\",\"popularity\":92.21434,\"poster_path\":\"\\/7GgZ6DGezkh3szFdvskH5XD4V0t.jpg\",\"original_language\":\"en\",\"original_title\":\"Maze Runner: The Death Cure\",\"genre_ids\":[28,9648,878,53,12,14],\"backdrop_path\":\"\\/bvbyidkMaBls1LTaIWYY6UmYTaL.jpg\",\"adult\":false,\"overview\":\"Thomas leads his group of escaped Gladers on their final and most dangerous mission yet. To save their friends, they must break into the legendary Last City, a WCKD-controlled labyrinth that may turn out to be the deadliest maze of all. Anyone who makes it out alive will get answers to the questions the Gladers have been asking since they first arrived in the maze.\",\"release_date\":\"2018-01-17\"},{\"vote_count\":857,\"id\":353616,\"video\":false,\"vote_average\":6.3,\"title\":\"Pitch Perfect 3\",\"popularity\":91.15406,\"poster_path\":\"\\/fchHLsLjFvzAFSQykiMwdF1051.jpg\",\"original_language\":\"en\",\"original_title\":\"Pitch Perfect 3\",\"genre_ids\":[35,10402],\"backdrop_path\":\"\\/1qIzlhLGPSm6TxlvXBWe0Q5er7O.jpg\",\"adult\":false,\"overview\":\"After the highs of winning the world championships, the Bellas find themselves split apart and discovering there aren't job prospects for making music with your mouth. But when they get the chance to reunite for an overseas USO tour, this group of awesome nerds will come together to make some music, and some questionable decisions, one last time.\",\"release_date\":\"2017-12-21\"},{\"vote_count\":11,\"id\":483877,\"video\":false,\"vote_average\":4.5,\"title\":\"15+ IQ Krachoot\",\"popularity\":90.176913,\"poster_path\":\"\\/1RxnEJzXYv5GgPszbHNsWTXPC6M.jpg\",\"original_language\":\"th\",\"original_title\":\"15 ไอคิวกระฉูด\",\"genre_ids\":[35],\"backdrop_path\":\"\\/fp3Iiap1hblJfqHrBDziqgl9zzB.jpg\",\"adult\":false,\"overview\":\"A teenage comedy film about a young Yorkie. \\\"Rising\\\" opened the first movie on screen.  The story of fun gangs of teenage age 15+ hormones gurgling at the curiosity to try on their love and sex has spread out.\",\"release_date\":\"2017-08-03\"},{\"vote_count\":7280,\"id\":245891,\"video\":false,\"vote_average\":7,\"title\":\"John Wick\",\"popularity\":88.652468,\"poster_path\":\"\\/5vHssUeVe25bMrof1HyaPyWgaP.jpg\",\"original_language\":\"en\",\"original_title\":\"John Wick\",\"genre_ids\":[28,53],\"backdrop_path\":\"\\/umC04Cozevu8nn3JTDJ1pc7PVTn.jpg\",\"adult\":false,\"overview\":\"Ex-hitman John Wick comes out of retirement to track down the gangsters that took everything from him.\",\"release_date\":\"2014-10-22\"},{\"vote_count\":3599,\"id\":399055,\"video\":false,\"vote_average\":7.3,\"title\":\"The Shape of Water\",\"popularity\":88.37511,\"poster_path\":\"\\/k4FwHlMhuRR5BISY2Gm2QZHlH5Q.jpg\",\"original_language\":\"en\",\"original_title\":\"The Shape of Water\",\"genre_ids\":[18,14,10749],\"backdrop_path\":\"\\/abirSHwWgKajV3hXhaIR5lcCIXe.jpg\",\"adult\":false,\"overview\":\"An other-worldly story, set against the backdrop of Cold War era America circa 1962, where a mute janitor working at a lab falls in love with an amphibious man being held captive there and devises a plan to help him escape.\",\"release_date\":\"2017-12-01\"\n" +
//                "\t\t}\n" +
//                "\t]\n" +
//                "}";
//    }

    private static URL buildURL(String link){
        Uri queryUri = Uri.parse(link).buildUpon()
                .appendQueryParameter("api_key", API_KEY)
                .build();
        try {
            URL url = new URL(queryUri.toString());
            return url;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

}
