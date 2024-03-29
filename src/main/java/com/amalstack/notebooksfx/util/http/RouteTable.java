package com.amalstack.notebooksfx.util.http;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;

public class RouteTable {
    private final Map<String, String> routeMap;
    private final BinaryOperator<String> nameCombiner;

    private RouteTable(Map<String, String> routeMap, BinaryOperator<String> nameCombiner) {
        this.routeMap = routeMap;
        this.nameCombiner = nameCombiner;
    }

    public static Builder builder() {
        return new Builder(new DotNameCombiner());
    }

    public static Builder builder(BinaryOperator<String> nameCombiner) {
        return new Builder(nameCombiner);
    }

    public static RouteTable empty() {
        return EmptyRouteTable.getInstance();
    }

    public static boolean isNullOrEmpty(RouteTable routeTable) {
        return routeTable == null || EmptyRouteTable.getInstance() == routeTable || routeTable.routeMap.isEmpty();
    }

    public String get(String routeName) {
        if (!routeMap.containsKey(routeName)) {
            throw new RouteNotFoundException(routeName);
        }
        return routeMap.get(routeName);
    }

    public String get(String... routeNames) {
        return get(combineNames(routeNames));
    }

    public String get(RouteName routeName) {
        return get(combineNames(routeName));
    }

    public String combineNames(String... names) {
        String result = "";
        for (String name : names) {
            result = nameCombiner.apply(result, name);
        }
        return result;
    }

    public String combineNames(RouteName routeName) {
        return combineNames(routeName.asArray());
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        routeMap.forEach((key, value) -> b
                .append(key)
                .append(" -> ")
                .append(value)
                .append("\n"));
        return b.toString();
    }

    public static final class Builder {
        private final Map<String, String> routeMap = new HashMap<>();
        private final BinaryOperator<String> nameCombiner;

        private Builder(BinaryOperator<String> nameCombiner) {
            this.nameCombiner = nameCombiner;
        }

        public Builder map(String name, String url) {
            routeMap.put(name, url);
            return this;
        }

        public Builder mapGroup(String groupName, String groupUrl, Consumer<Builder> routes) {
            var builder = new Builder(nameCombiner);
            routes.accept(builder);

            for (var entry : builder.routeMap.entrySet()) {
                String routeName = nameCombiner.apply(groupName, entry.getKey());
                String routeUrl = combineUrl(groupUrl, entry.getValue());
                routeMap.put(routeName, routeUrl);
            }

            return this;
        }


        private String combineUrl(String url1, String url2) {
            return url1 + url2;
        }

        public RouteTable build() {
            return new RouteTable(routeMap, nameCombiner);
        }
    }

    private static class DotNameCombiner implements BinaryOperator<String> {
        @Override
        public String apply(String name1, String name2) {
            if (name1 == null || name1.isBlank()) {
                return name2;
            }
            if (name2 == null || name2.isBlank()) {
                return name1;
            }
            return name1 + "." + name2;
        }
    }

    private static class EmptyRouteTable extends RouteTable {
        private static RouteTable instance;

        EmptyRouteTable() {
            super(Collections.emptyMap(), new DotNameCombiner());
        }

        public static RouteTable getInstance() {
            if (instance == null) {
                instance = new EmptyRouteTable();
            }
            return instance;
        }

        @Override
        public String get(String routeName) {
            throw new RouteNotFoundException(routeName);
        }

        @Override
        public String toString() {
            return "Empty Route Table";
        }
    }
}
