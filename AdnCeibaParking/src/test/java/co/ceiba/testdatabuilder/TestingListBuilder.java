package co.ceiba.testdatabuilder;

import java.util.ArrayList;
import java.util.List;

import co.ceiba.domain.ParkingTicket;

public class TestingListBuilder {

	public List<ParkingTicket> parkingTicketsEmptyList() {
		return new ArrayList<>();
	}
		public List<ParkingTicket> parkingTicketsListWithElements(int elements) {
			List<ParkingTicket> l = new ArrayList<>();
			while (l.size()< elements) {
				l.add(new ParkingTicket());
			}
			return l;
	}
}
