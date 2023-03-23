package com.amalstack.notebooksfx.util.controls;

/**
 * @author Amal Krishna
 */
public final class IdUtil {

    /**
     * Combines the IDs of multiple controls by concatenating them.
     * This is useful for generating unique IDs for controls created through code by combining them with the ID of the parent node(s).
     *
     * @param ids The IDs to combine
     * @return The concatenated ID.
     */
    public static String combine(String... ids) {
        String delimiter = "";
        return String.join(delimiter, ids);
    }
}

