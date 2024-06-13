package org.example;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class CountLines {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Uso: java CountLines <phy|loc> <ruta/patrón>");
            System.exit(1);
        }

        String mode = args[0];
        String pathPattern = args[1];

        try {
            long startTime = System.nanoTime();
            List<Path> files = getFiles(pathPattern);
            long totalLines = 0;

            for (Path file : files) {
                totalLines += countLines(file, mode);
            }

            long endTime = System.nanoTime();
            double elapsedTime = (endTime - startTime) / 1e9; // Convertir a segundos

            System.out.println("Líneas " + mode + ": " + totalLines);
            System.out.println("Tiempo dedicado (horas): " + elapsedTime / 3600);
            System.out.println("LOC/h: " + totalLines / elapsedTime);
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }
    }

    private static List<Path> getFiles(String pathPattern) throws IOException {
        final List<Path> fileList = new ArrayList<>();
        PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:" + pathPattern);
        Path startPath = Paths.get("").toAbsolutePath();
        boolean isRecursive = pathPattern.contains("**");

        Files.walkFileTree(Paths.get("").toAbsolutePath(), new SimpleFileVisitor<Path>() {

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:" + pathPattern);
                if (matcher.matches(startPath.relativize(file))) {
                    fileList.add(file);
                }
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                if (!isRecursive && !dir.equals(startPath)) {
                    return FileVisitResult.SKIP_SUBTREE;
                }
                return FileVisitResult.CONTINUE;
            }

        });

        return fileList;
    }

    private static long countLines(Path file, String mode) throws IOException {
        long lines = 0;
        final boolean[] inBlockComment = {false};

        try (Stream<String> stream = Files.lines(file)) {
            if (mode.equals("phy")) {
                lines = stream.count();
            } else if (mode.equals("loc")) {
                lines = stream.filter(line -> {
                    String trimmedLine = line.trim();
                    if (trimmedLine.isEmpty() || trimmedLine.startsWith("//")) {
                        return false;
                    }
                    if (inBlockComment[0]) {
                        if (trimmedLine.endsWith("*/")) {
                            inBlockComment[0] = false;
                        }
                        return false;
                    }
                    if (trimmedLine.startsWith("/*")) {
                        if (!trimmedLine.endsWith("*/")) {
                            inBlockComment[0] = true;
                        }
                        return false;
                    }
                    return true;
                }).count();
            }
        }

        return lines;
    }
}
