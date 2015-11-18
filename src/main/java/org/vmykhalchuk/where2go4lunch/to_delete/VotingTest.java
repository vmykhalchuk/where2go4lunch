package org.vmykhalchuk.where2go4lunch.to_delete;

import org.vmykhalchuk.where2go4lunch.service.VotingService;

public class VotingTest {

	public static void _main(String[] args) {
		System.out.println(VotingService.getDateByTmstmp(2560));
		testIsTmstmpLessThan();
	}
	
	public static void testIsTmstmpLessThan() {
		long tmstmp17Nov20_35GMT2 = 1447785334868L;
		
		System.out.println("true" + VotingService.isTmstmpLessThan(tmstmp17Nov20_35GMT2, 20, 35));
		System.out.println("false" + VotingService.isTmstmpLessThan(tmstmp17Nov20_35GMT2, 20, 34));
		System.out.println("true" + VotingService.isTmstmpLessThan(tmstmp17Nov20_35GMT2, 21, 34));
	}

}
