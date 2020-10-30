package entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class DetailsURLCode {

    public String code;
    public String registrationUrl;

    public DetailsURLCode(String code, String registrationUrl) {
        this.code = code;
        this.registrationUrl = registrationUrl;
    }
}
