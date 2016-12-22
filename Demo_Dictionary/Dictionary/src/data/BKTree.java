package data;
//package inteldt.todonlp.spellchecker;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * BK����������������ƴд�����ѯ
 *
 */
public class BKTree<T>{
    private final MetricSpace<T> metricSpace;
    
    private Node<T> root;
    
    public BKTree(MetricSpace<T> metricSpace) {
        this.metricSpace = metricSpace;
    }
    
    /**
     * ����ĳһ������Ԫ�ش���BK��
     */
    public static <E> BKTree<E> mkBKTree(MetricSpace<E> ms, Collection<E> elems) {
        
        BKTree<E> bkTree = new BKTree<E>(ms);
        
        for (E elem : elems) {
            bkTree.put(elem);
        }
        
        return bkTree;
    }

    /**
     * BK�������Ԫ��
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
     * ��ѯ����Ԫ��
     * 
     * @param term
     *         ����ѯ��Ԫ��
     * @param radius
     *         ���Ƶľ��뷶Χ
     * @return
     *         ������뷶Χ������Ԫ��
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
         *  ��һ��map�洢�ӽڵ�
         */
        private final Map<Double, Node<T>> children;
        
        public Node(T term) {
            this.value = term;
            this.children = new HashMap<Double, BKTree.Node<T>>();
        }
        
        public void add(MetricSpace<T> ms, T value) {
            // value�븸�ڵ�ľ���
            Double distance = ms.distance(this.value, value);
            
            // ����Ϊ0����ʾԪ����ͬ������
            if (distance == 0) {
                return;
            }
            
            // �Ӹ��ڵ���ӽڵ��в���child���������Ϊdistance
            Node<T> child = children.get(distance);
            
            
            if (child == null) {
                // �����븸�ڵ�Ϊdistance���ӽڵ㲻���ڣ���ֱ�����һ���µ��ӽڵ�
                children.put(distance, new Node<T>(value));
            } else {
                // �����븸�ڵ�Ϊdistance�ӽڵ���ڣ���ݹ�Ľ�value��ӵ����ӽڵ���
                child.add(ms, value);
            }
        }
        
        public void query(MetricSpace<T> ms, T term, double radius, Set<T> results) {
            
            double distance = ms.distance(this.value, term);
            
            // �븸�ڵ�ľ���С����ֵ������ӵ�������У�����������Ѱ��
            if (distance <= radius) {
                results.add(this.value);
            }
            
            // �ӽڵ�ľ�������С�����������֮��ġ�
            // �ɶ����ռ��d(x,y) + d(y,z) >= d(x,z)��һ�����в��ҵ�value���ӽڵ�ľ��뷶Χ���£�
            // min = {1,distance -radius}, max = distance + radius
            for (double i = Math.max(distance - radius, 1); i <= distance + radius; ++i) {
                
                Node<T> child = children.get(i);
                
                // �ݹ����
                if (child != null) {
                    child.query(ms, term, radius, results);
                }
                
            }    
        }
    }
}