package com.rtecsoft.alpha.confluentschemaregistrydemo.web.resources;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.Collection;

@Getter
@SuperBuilder
public class GetSubjectsResponse extends ResponseBase {

    private Collection<String> subjects;
}
