package data;

public interface MetricSpace<T> {

    double distance(T a, T b);
    
}