package llama.bettermappet.api.scripts.code;

import java.nio.file.Path;

public class ScriptClientFile {
    Path path;

    public ScriptClientFile(Path path) {
        this.path = path;
    }

    public Path getPath() {
        return this.path;
    }

    public String getFullName() {
        return this.path.getFileName().toString();
    }

    public String getName() {
        int dotIndex = this.getFullName().lastIndexOf('.');
        return this.getFullName().substring(0, dotIndex);
    }

    public String getExtension() {
        if (this.isDirectory()) {
            return "";
        }
        return this.getFullName().substring(this.getFullName().lastIndexOf('.'));
    }

    public boolean isFile() {
        return this.path.toFile().isFile();
    }

    public boolean isDirectory() {
        return this.path.toFile().isDirectory();
    }
}
