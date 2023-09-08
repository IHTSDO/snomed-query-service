package org.ihtsdo.otf.sqs.service.dto;

import java.util.List;

public record ConceptIdResults(List<Long> conceptIds, int offset, int total, int limit) {


}
