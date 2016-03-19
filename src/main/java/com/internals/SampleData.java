package com.internals;

public class SampleData {
    private String identifier;
    private String source;
    private String postorcomment;

    public SampleData(String identifier, String source, String postorcomment) {
        this.identifier = identifier;
        this.source = source;
        this.postorcomment = postorcomment;
    }

    public String getpostorcomment() {
        return postorcomment;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setpostorcomment(String postorcomment) {
        this.postorcomment = postorcomment;
    }
}
