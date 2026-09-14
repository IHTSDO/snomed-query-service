package org.ihtsdo.otf.sqs.service;

import org.ihtsdo.otf.snomedboot.domain.ConceptConstants;
import org.ihtsdo.otf.snomedboot.factory.implementation.standard.ComponentStore;
import org.ihtsdo.otf.snomedboot.factory.implementation.standard.ComponentStoreComponentFactoryImpl;
import org.ihtsdo.otf.sqs.service.store.RamReleaseStore;
import org.ihtsdo.otf.sqs.service.store.ReleaseStore;
import org.ihtsdo.otf.snomedboot.factory.LoadingProfile;
import org.ihtsdo.otf.sqs.service.exception.ServiceException;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * A constraint operator applied to a member-of expression must distribute over
 * the MEMBERS of the refset.
 *
 * <p>{@code << ^X} is the members and their descendants. It used to return the
 * members alone, because the operator was discarded on the way to the index, so
 * a caller deciding which concepts are permitted something saw every descendant
 * of a member as not permitted it.
 */
public class MemberOfConstraintOperatorTest {

	private static final String REFSET = "700043003";
	private static final String MEMBER = "362961001";
	private static final String CHILD_OF_MEMBER = "363787002";
	private static final String GRANDCHILD_OF_MEMBER = "123037004";
	private static final String NON_MEMBER = "410662002";
	/**
	 * A second refset whose member is the GRANDCHILD, so it has ancestors to
	 * walk up to. The first member is a root here and cannot exercise {@code >}.
	 */
	private static final String DEEP_REFSET = "450990004";

	private SnomedQueryService queryService;

	@Before
	public void setup() throws Exception {
		queryService = new SnomedQueryService(new RefsetTaxonomy().build());
	}

	@Test
	public void membersOnlyWithoutAnOperator() throws ServiceException {
		assertEquals(List.of(Long.valueOf(MEMBER)), ids("^ " + REFSET));
	}

	@Test
	public void descendantOrSelfReturnsTheMembersAndEverythingBeneathThem() throws ServiceException {
		assertEquals(
				List.of(Long.valueOf(GRANDCHILD_OF_MEMBER), Long.valueOf(MEMBER), Long.valueOf(CHILD_OF_MEMBER)),
				sorted("<< ^ " + REFSET));
	}

	@Test
	public void descendantExcludesTheMembersThemselves() throws ServiceException {
		assertEquals(
				List.of(Long.valueOf(GRANDCHILD_OF_MEMBER), Long.valueOf(CHILD_OF_MEMBER)),
				sorted("< ^ " + REFSET));
	}

	@Test
	public void ancestorExcludesTheMembersThemselves() throws ServiceException {
		assertEquals(
				List.of(Long.valueOf(MEMBER), Long.valueOf(CHILD_OF_MEMBER)),
				sorted("> ^ " + DEEP_REFSET));
	}

	@Test
	public void ancestorOrSelfReturnsTheMembersAndEverythingAboveThem() throws ServiceException {
		assertEquals(
				List.of(Long.valueOf(GRANDCHILD_OF_MEMBER), Long.valueOf(MEMBER), Long.valueOf(CHILD_OF_MEMBER)),
				sorted(">> ^ " + DEEP_REFSET));
	}

	/** A refset nothing belongs to resolves to nothing, in every direction. */
	@Test
	public void anEmptyRefsetReturnsNothingInEveryDirection() throws ServiceException {
		for (String operator : List.of("^ ", "< ^ ", "<< ^ ", "> ^ ", ">> ^ ")) {
			assertEquals(operator + "of an empty refset must return nothing",
					List.of(), ids(operator + NON_MEMBER));
		}
	}

	@Test
	public void aConceptOutsideTheMemberSubtreeIsNeverReturned() throws ServiceException {
		for (String ecl : List.of("^ " + REFSET, "< ^ " + REFSET, "<< ^ " + REFSET)) {
			assertEquals(ecl + " must not return a concept outside the member subtree",
					false, ids(ecl).contains(Long.valueOf(NON_MEMBER)));
		}
	}

	private List<Long> ids(String ecl) throws ServiceException {
		return queryService.eclQueryReturnConceptIdentifiers(ecl, 0, -1).conceptIds();
	}

	private List<Long> sorted(String ecl) throws ServiceException {
		return ids(ecl).stream().sorted().toList();
	}

	/** A refset whose single member has a child and a grandchild. */
	private static final class RefsetTaxonomy extends ReleaseImportManager {

		private final ComponentStore componentStore = new ComponentStore();
		private final ComponentStoreComponentFactoryImpl factory =
				new ComponentStoreComponentFactoryImpl(componentStore);

		private ReleaseStore build() throws Exception {
			concept(REFSET, "Example reference set (foundation metadata concept)");
			concept(MEMBER, "Member (finding)");
			concept(NON_MEMBER, "Unrelated (finding)");
			child(CHILD_OF_MEMBER, "Child of member (finding)", MEMBER);
			child(GRANDCHILD_OF_MEMBER, "Grandchild of member (finding)", CHILD_OF_MEMBER);
			factory.addConceptReferencedInRefsetId(REFSET, MEMBER);
			concept(DEEP_REFSET, "Deep reference set (foundation metadata concept)");
			factory.addConceptReferencedInRefsetId(DEEP_REFSET, GRANDCHILD_OF_MEMBER);
			return writeToIndex(componentStore.getConcepts(), new RamReleaseStore(),
					LoadingProfile.light.withoutInactiveConcepts());
		}

		private void concept(String id, String fsn) {
			factory.newConceptState("", 1L, id, "20150731", "1", "900000000000207008", "900000000000074008");
			factory.addConceptFSN(id, fsn);
		}

		private void child(String id, String fsn, String parentId) {
			concept(id, fsn);
			factory.addInferredConceptParent(id, parentId);
			factory.newRelationshipState("", 1L, "", "20150731", "1", "", id, parentId, "0",
					ConceptConstants.isA, "900000000000011006", "");
		}
	}
}
