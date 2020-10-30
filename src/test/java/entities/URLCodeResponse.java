package entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class URLCodeResponse {
    public Boolean success;
    public DetailsURLCode details;

    public URLCodeResponse(Boolean success, DetailsURLCode details) {
        this.success = success;
        this.details = details;
    }

}
