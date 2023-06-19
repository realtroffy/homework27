package by.epam.training.homework27.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Lob;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Arrays;

@Embeddable
public class AttachmentFile implements Serializable {

    private static final long serialVersionUID = 9213481264682549175L;

    @NotBlank
    private String name;

    private String type;

    @Lob
    @Column(name = "ATTACHMENT", columnDefinition = "BLOB")
    private byte[] file;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    @Override
    public String toString() {
        return "AttachmentFile{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", file=" + Arrays.toString(file) +
                '}';
    }
}
