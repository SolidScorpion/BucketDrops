package apripachkin.com.bucketdrops;

/**
 * Created by root on 18.04.16.
 */
public interface Filter {
    int NONE = 0;
    int MOST_TIME_LEFT = 1;
    int LEAST_TIME_LEFT = 2;
    int COMPLETED = 3;
    int INCOMPLETE = 4;
}
