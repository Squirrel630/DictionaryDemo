package data;
//package inteldt.todonlp.spellchecker;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * BK树，可以用来进行拼写纠错查询
 *
 */
public class BKTree<T>{
    private final MetricSpace<T> metricSpace;
    
    private Node<T> root;
    
    public BKTree(MetricSpace<T> metricSpace) {
        this.metricSpace = metricSpace;
    }
    
    /**
     * 根据某一个集合元素创建BK树
     */
    public static <E> BKTree<E> mkBKTree(MetricSpace<E> ms, Collection<E> elems) {
        
        BKTree<E> bkTree = new BKTree<E>(ms);
        
        for (E elem : elems) {
            bkTree.put(elem);
        }
        
        return bkTree;
    }

    /**
     * BK树中添加元素
     * 
     * @param term
     */
    public void put(T term) {
        if (root == null) {
            root = new Node<T>(term);
        } else {
            root.add(metricSpace, term);
        }
    }
    
    /**
     * 查询相似元素
     * 
     * @param term
     *         待查询的元素
     * @param radius
     *         相似的距离范围
     * @return
     *         满足距离范围的所有元素
     */
    public Set<T> query(T term, double radius) {
        
        Set<T> results = new HashSet<T>();
        
        if (root != null) {
            root.query(metricSpace, term, radius, results);
        }
        
        return results;
    }
    
    private static final class Node<T> {
    
        private final T value;
        
        /**
         *  用一个map存储子节点
         */
        private final Map<Double, Node<T>> children;
        
        public Node(T term) {
            this.value = term;
            this.children = new HashMap<Double, BKTree.Node<T>>();
        }
        
        public void add(MetricSpace<T> ms, T value) {
            // value与父节点的距离
            Double distance = ms.distance(this.value, value);
            
            // 距离为0，表示元素相同，返回
            if (distance == 0) {
                return;
            }
            
            // 从父节点的子节点中查找child，满足距离为distance
            Node<T> child = children.get(distance);
            
            
            if (child == null) {
                // 若距离父节点为distance的子节点不存在，则直接添加一个新的子节点
                children.put(distance, new Node<T>(value));
            } else {
                // 若距离父节点为distance子节点存在，则递归的将value添加到该子节点下
                child.add(ms, value);
            }
        }
        
        public void query(MetricSpace<T> ms, T term, double radius, Set<T> results) {
            
            double distance = ms.distance(this.value, term);
            
            // 与父节点的距离小于阈值，则添加到结果集中，并继续向下寻找
            if (distance <= radius) {
                results.add(this.value);
            }
            
            // 子节点的距离在最小距离和最大距离之间的。
            // 由度量空间的d(x,y) + d(y,z) >= d(x,z)这一定理，有查找的value与子节点的距离范围如下：
            // min = {1,distance -radius}, max = distance + radius
            for (double i = Math.max(distance - radius, 1); i <= distance + radius; ++i) {
                
                Node<T> child = children.get(i);
                
                // 递归调用
                if (child != null) {
                    child.query(ms, term, radius, results);
                }
                
            }    
        }
    }
}