package com.snoozieapp.app.backend.objects.audio;

public class Audio {

    // Fields are subject to be added/removed
    // Save commenting for the end

    private boolean isLocal;
    private boolean isUrl;
    private String filePath;
    private String urlPath;

    public Audio(boolean isLocal, boolean isUrl, String filePath, String urlPath)
    {
        // add check; cannot be both
        this.isLocal = isLocal;
        this.isUrl = isLocal;
        // same with these, the correct bool has to be set
        this.filePath = filePath;
        this.urlPath = urlPath;
    }

    public Audio loadFileFromPath(String path)
    {
        return null;
    }

    public Audio loadFileFromUrl(String url)
    {
        return null;
    }

}
