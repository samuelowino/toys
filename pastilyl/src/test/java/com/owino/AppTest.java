package com.owino;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
public class AppTest {
    private static final String testFile = "test.sql";
    @BeforeEach
    public void setUp() throws IOException {
        var path = Path.of(testFile);
        Files.createFile(path);
        Files.writeString(path, """
                During the synchronization process, several records were updated.
                The user profile was linked to session ID 3fa85f64-5717-4562-b3fc-2c963f66afa6,
                while the preferences module referenced config
                ID 6b1f5e2d-9a6a-41d3-8a7c-99cf83ad2b90. Additionally, the activity log
                was assigned entry ID f47ac10b-58cc-4372-a567-0e02b2c3d479, ensuring that
                every change could be tracked independently. For debugging purposes, error
                trace ID a3c8e2b4-3245-4f89-9c5c-3bcaf6341230 was also recorded.
                """);
    }
    @Test
    public void shouldReadFileTest() throws IOException {
        var contents = App.readLines(testFile);
        assertThat(contents).isNotEmpty();
        assertThat(contents.size()).isGreaterThan(0);
    }
    @Test
    public void shouldReplaceUuidsTest() {
        List<String> originalLines = List.of(
                "During the synchronization process, several records were updated.",
                "he user profile was linked to session ID 3fa85f64-5717-4562-b3fc-2c963f66afa6,",
                "while the preferences module referenced config ID 6b1f5e2d-9a6a-41d3-8a7c-99cf83ad2b90"
        );
        var finalLines = App.replaceUuids(originalLines);
        assertThat(finalLines).isNotEmpty();
        assertThat(finalLines.getFirst()).isEqualTo("During the synchronization process, several records were updated.");
        assertThat(finalLines.get(1)).isNotEqualTo("he user profile was linked to session ID 3fa85f64-5717-4562-b3fc-2c963f66afa6,");
        assertThat(finalLines.get(2)).isNotEqualTo("while the preferences module referenced config ID 6b1f5e2d-9a6a-41d3-8a7c-99cf83ad2b90");
    }
    @Test
    public void shouldOverwriteFileTest() throws IOException {
        App.overwriteFile(List.of("**empty-file**"),testFile);
        var actualContent = Files.readAllLines(Path.of(testFile));
        assertThat(actualContent).isNotEmpty();
        assertThat(actualContent.getFirst()).isEqualTo("**empty-file**");
    }
    @Test
    public void shouldIncludeAllLinesInOverwriteTest() throws IOException {
        App.overwriteFile(List.of(
                "**empty-line-1**",
                "**empty-line-2**",
                "**empty-line-3**",
                "**empty-line-4**"
        ),testFile);
        var actualContent = Files.readAllLines(Path.of(testFile));
        assertThat(actualContent).isNotEmpty();
        assertThat(actualContent.size()).isEqualTo(4);
        assertThat(actualContent.getFirst()).isEqualTo("**empty-line-1**");
        assertThat(actualContent.get(1)).isEqualTo("**empty-line-2**");
        assertThat(actualContent.get(2)).isEqualTo("**empty-line-3**");
        assertThat(actualContent.get(3)).isEqualTo("**empty-line-4**");
    }
    @AfterEach
    public void cleanUp() throws IOException {
        Files.delete(Path.of(testFile));
    }
}
