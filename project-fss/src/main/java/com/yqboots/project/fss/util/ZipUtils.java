package com.yqboots.project.fss.util;

import com.yqboots.project.fss.core.support.FileType;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by Administrator on 2016-06-10.
 */
public class ZipUtils {
    private static final int BUFFER = 2048;

    public static Path compress(Path dir) throws IOException {
        Assert.isTrue(Files.exists(dir), "The directory does not exist: " + dir.toAbsolutePath());
        Assert.isTrue(Files.isDirectory(dir), "Should be a directory: " + dir.toAbsolutePath());

        Path result = Paths.get(dir.toAbsolutePath() + FileType.DOT_ZIP);
        try (final ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(result.toFile())))) {
            // out.setMethod(ZipOutputStream.DEFLATED);
            final byte data[] = new byte[BUFFER];
            // get a list of files from current directory
            Files.walkFileTree(dir, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(final Path path, final BasicFileAttributes attrs) throws IOException {
                    final File file = path.toFile();

                    // compress to relative directory, not absolute
                    final String root = StringUtils.substringAfter(file.getParent(), dir.toString());
                    try (final BufferedInputStream origin = new BufferedInputStream(new FileInputStream(file), BUFFER)) {
                        final ZipEntry entry = new ZipEntry(root + File.separator + path.getFileName());
                        out.putNextEntry(entry);
                        int count;
                        while ((count = origin.read(data, 0, BUFFER)) != -1) {
                            out.write(data, 0, count);
                        }
                    }

                    return FileVisitResult.CONTINUE;
                }
            });
        }

        return result;
    }
}
