package org.jcm.gs.rest.service;

import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
// 求哪些source 最后都归集到 target D
public class ScratchService {
    @Getter
    @ToString
    static class Mapping {
        String source;
        String target;

        Set<String> sourceList;

        public Mapping(String source, String target) {
            this.source = source;
            this.target = target;
        }

        public Mapping(String target, Collection<String> sourceList) {
            this.target = target;
            this.sourceList = new HashSet<>(sourceList);
        }
    }

    public static void main(String[] args) {

        List<Mapping> mappings = new ArrayList<>();
        mappings.add(new Mapping("A", "B"));
        mappings.add(new Mapping("B", "C"));
        mappings.add(new Mapping("G", "C"));
        mappings.add(new Mapping("C", "D"));
        mappings.add(new Mapping("F", "D"));

        mappings.add(new Mapping("X", "Y"));
        mappings.add(new Mapping("D", "E"));
//        mappings.add(new Mapping("E", "A"));

        mappings.add(new Mapping("E", "M"));
        mappings.add(new Mapping("Y", "M"));


        test1(mappings);


    }


    private static void test1(List<Mapping> mappings) {
        Map<String, String> sourceMap = mappings
                .stream().
                collect(Collectors.toMap(Mapping::getSource, Mapping::getTarget));
        log.info("原始值{}", sourceMap);
        Set<Map.Entry<String, String>> entries = sourceMap.entrySet();
        Map<String, Set<String>> mapAll = new HashMap<>();


        entries.forEach(x -> {
            Mapping mapping = getMap(x.getKey(), sourceMap);
            Set<String> values = mapAll.get(mapping.getTarget());
            if (CollectionUtils.isNotEmpty(values)) {
                values.addAll(mapping.getSourceList());
                mapAll.put(mapping.getTarget(), values);
            } else {
                mapAll.put(mapping.getTarget(), mapping.getSourceList());
            }

        });
        log.info("归集后:{}", mapAll);

    }

    private static Mapping getMap(String key, Map<String, String> sourceMap) {
        List<String> list = new ArrayList<>(sourceMap.size());
        list.add(key);
        String lastKey = null;
        String value = sourceMap.get(key);
        while (StringUtils.isNotBlank(value)) {
            String targetSourceValue = sourceMap.get(value);
            if (StringUtils.isBlank(targetSourceValue)) {
                lastKey = value;
            } else {
                if (list.contains(value)) {
                    break;
                }
                list.add(value);
            }
            value = targetSourceValue;
        }
        return new Mapping(lastKey, list);
    }


}
