package com.epam.rd.autocode.floodfill;

import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class FloodFillParametrized implements FloodFill {

    @Override
    public void flood(String map, FloodLogger logger) {
        StringBuilder stringBuilder = new StringBuilder(map);
        int lineLength = getLineLength(stringBuilder);
        logger.log(map);

        changePositions(stringBuilder, lineLength);

        if (isLand(stringBuilder)) {
            flood(stringBuilder.toString(), logger);
        } else {
            logger.log(stringBuilder.toString());
        }
    }

    private boolean isLand(StringBuilder stringBuilder) {
        return IntStream.range(0, stringBuilder.length())
                .anyMatch(element -> stringBuilder.charAt(element) == LAND);
    }

    private void changePositions(StringBuilder stringBuilder, int lineLength) {
        List<Integer> waterIndexes = getWaterIndexList(stringBuilder);
        waterIndexes.forEach(index -> {
            setWaterLeft(stringBuilder, index);
            setWaterRight(stringBuilder, index);
            setWaterUp(stringBuilder, lineLength, index);
            setWaterDown(stringBuilder, lineLength, index);
        });
    }

    private void setWaterDown(StringBuilder stringBuilder, int lineLength, Integer elementIndex) {
        if ((elementIndex + lineLength) < stringBuilder.length()
                && (elementIndex + lineLength) >= 0
                && stringBuilder.charAt(elementIndex + lineLength) != '\n') {
            stringBuilder.setCharAt(elementIndex + lineLength, WATER);
        }
    }

    private void setWaterUp(StringBuilder stringBuilder, int lineLength, Integer index) {
        if ((index - lineLength) < stringBuilder.length() && (index - lineLength) >= 0
                && stringBuilder.charAt(index - lineLength) != '\n') {
            stringBuilder.setCharAt(index - lineLength, WATER);
        }
    }

    private void setWaterRight(StringBuilder stringBuilderMap, Integer index) {
        if ((index + 1) < stringBuilderMap.length() && (index + 1) >= 0
                && stringBuilderMap.charAt(index + 1) != '\n') {
            stringBuilderMap.setCharAt(index + 1, WATER);
        }
    }

    private void setWaterLeft(StringBuilder stringBuilderMap, Integer index) {
        if ((index - 1) < stringBuilderMap.length()
                && (index - 1) >= 0
                && stringBuilderMap.charAt(index - 1) != '\n') {
            stringBuilderMap.setCharAt(index - 1, WATER);
        }
    }

    private int getLineLength(StringBuilder stringBuilder) {
        return stringBuilder.indexOf("\n") + 1;
    }

    private List<Integer> getWaterIndexList(StringBuilder stringBuilder) {
        return IntStream.range(0, stringBuilder.length())
                .filter(index -> stringBuilder.charAt(index) == WATER)
                .boxed()
                .collect(toList());
    }
}
