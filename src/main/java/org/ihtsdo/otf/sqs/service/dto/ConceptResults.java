package org.ihtsdo.otf.sqs.service.dto;

import java.util.List;

public record ConceptResults(List<ConceptResult> items, int offset, int total, int limit) {
}
