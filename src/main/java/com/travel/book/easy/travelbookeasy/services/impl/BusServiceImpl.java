package com.travel.book.easy.travelbookeasy.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travel.book.easy.travelbookeasy.api.common.ApiException;
import com.travel.book.easy.travelbookeasy.api.dto.BusDto;
import com.travel.book.easy.travelbookeasy.api.dto.SearchFilterDto;
import com.travel.book.easy.travelbookeasy.db.model.Bus;
import com.travel.book.easy.travelbookeasy.db.model.Company;
import com.travel.book.easy.travelbookeasy.db.repository.BusRepository;
import com.travel.book.easy.travelbookeasy.db.repository.CompanyRepository;
import com.travel.book.easy.travelbookeasy.services.interfaces.BusService;
import com.travel.book.easy.travelbookeasy.services.interfaces.LocationService;
import com.travel.book.easy.travelbookeasy.util.SearchUtil;

@Service
public class BusServiceImpl implements BusService {

	@Autowired
	private BusRepository busRepository;

	@Autowired
	private LocationService locationService;

	@Autowired
	private CompanyRepository companyRepository;

	@Override
	public BusDto createBusRecord(BusDto busDto, long companyId) {

		Optional<Company> company = companyRepository.findById(companyId);

		if (!company.isPresent()) {
			throw new ApiException("Company not found");
		}

		Bus bus = new Bus();
		bus.setCompany(company.get());
		bus.setName(busDto.getName());
		bus.setDepartDate(busDto.getDepartDate());
		bus.setArriveDate(busDto.getArriveDate());
		bus.setLocationFrom(locationService.createLocation(busDto.getLocationFrom().getName()));
		bus.setLocationTo(locationService.createLocation(busDto.getLocationTo().getName()));
		bus.setPrice(busDto.getPrice());
		bus.setMaxSeats(busDto.getMaxSeats());

		Bus savedBus = busRepository.saveAndFlush(bus);

		return BusDto.of(savedBus);
	}

	@Override
	public BusDto getBus(long busId) {

		Optional<Bus> bus = busRepository.findById(busId);

		if (!bus.isPresent()) {
			throw new ApiException("Bus not found");
		}

		return BusDto.of(bus.get());
	}

	@Override
	public List<BusDto> findAllBuses() {

		return busRepository.findAllBuses().stream().map(b -> BusDto.of(b)).collect(Collectors.toList());
	}

	@Override
	public List<BusDto> searchBuses(SearchFilterDto searchFilterDto) {
		if (SearchUtil.checkAllFilter(searchFilterDto)) {
			return busRepository.findBusesByAllRequirements(searchFilterDto.getLocationFrom(),
					searchFilterDto.getLocationTo(), searchFilterDto.getDate()).stream().map(b -> BusDto.of(b))
					.collect(Collectors.toList());
		} else if (SearchUtil.checkFitlerWitPriceWithoutRating(searchFilterDto)) {
			return busRepository
					.findBusesByLocationAndDateAndPriceWithoutRating(searchFilterDto.getLocationFrom(),
							searchFilterDto.getLocationTo(), searchFilterDto.getDate())
					.stream().map(b -> BusDto.of(b)).collect(Collectors.toList());
		} else if (SearchUtil.checkFilterWithRatingWithoutPrice(searchFilterDto)) {
			return busRepository
					.findBusesByLocationAndDateAndRatingWithoutPrice(searchFilterDto.getLocationFrom(),
							searchFilterDto.getLocationTo(), searchFilterDto.getDate())
					.stream().map(b -> BusDto.of(b)).collect(Collectors.toList());
		} else if (SearchUtil.checkFilterWithPriceAndRatingWithoutDate(searchFilterDto)) {
			return busRepository
					.findBusesByLocationAndPriceAndRatingWithoutDate(searchFilterDto.getLocationFrom(),
							searchFilterDto.getLocationTo())
					.stream().map(b -> BusDto.of(b)).collect(Collectors.toList());
		} else if (SearchUtil.checkFilterWithPriceWithoutDateAndRating(searchFilterDto)) {
			return busRepository
					.findBusesByLocationAndPriceWithoutDateAndRating(searchFilterDto.getLocationFrom(),
							searchFilterDto.getLocationTo())
					.stream().map(b -> BusDto.of(b)).collect(Collectors.toList());
		} else if (SearchUtil.checkFilterWithRatingWithoutDateAndPrice(searchFilterDto)) {
			return busRepository
					.findTrainByLocationAndRatingWithoutDateAndPrice(searchFilterDto.getLocationFrom(),
							searchFilterDto.getLocationTo())
					.stream().map(b -> BusDto.of(b)).collect(Collectors.toList());
		} else if (SearchUtil.checkoFilterOnlyWithPrice(searchFilterDto)) {
			return busRepository.findBusesByPrice().stream().map(b -> BusDto.of(b)).collect(Collectors.toList());
		} else if (SearchUtil.checkFilterOnlyRating(searchFilterDto)) {
			return busRepository.findBusesByRating().stream().map(b -> BusDto.of(b)).collect(Collectors.toList());
		} else if (SearchUtil.checkFilterOnlyLocation(searchFilterDto)) {
			return busRepository.findBusesByLocation(searchFilterDto.getLocationFrom(), searchFilterDto.getLocationTo())
					.stream().map(b -> BusDto.of(b)).collect(Collectors.toList());
		} else if (SearchUtil.checkFilterLocationAndDateWithoutPriceAndRating(searchFilterDto)) {
			return busRepository
					.findBusesByLocationAndDateWithoutPriceAndRating(searchFilterDto.getLocationFrom(),
							searchFilterDto.getLocationTo(), searchFilterDto.getDate())
					.stream().map(b -> BusDto.of(b)).collect(Collectors.toList());
		}

		return busRepository.findAllBuses().stream().map(b -> BusDto.of(b)).collect(Collectors.toList());
	}

	@Override
	public BusDto updateBus(BusDto busDto) {
		Optional<Bus> bus = busRepository.findById(busDto.getId());

		if (!bus.isPresent()) {
			throw new ApiException("Bus not found");
		}

		if (busDto.getName() != null) {
			bus.get().setName(busDto.getName());
		}
		if (busDto.getCompany() != null && busDto.getCompany().getId() != 0) {
			bus.get().setCompany(companyRepository.getOne(busDto.getCompany().getId()));
		}
		if (busDto.getLocationFrom().getName() != null) {
			bus.get().setLocationFrom(locationService.createLocation(busDto.getLocationFrom().getName()));
		}
		if (busDto.getLocationTo().getName() != null) {
			bus.get().setLocationTo(locationService.createLocation(busDto.getLocationTo().getName()));
		}
		if (busDto.getDepartDate() != null) {
			bus.get().setDepartDate(busDto.getDepartDate());
		}
		if (busDto.getArriveDate() != null) {
			bus.get().setArriveDate(busDto.getArriveDate());
		}
		if (busDto.getPrice() != null) {
			bus.get().setPrice(busDto.getPrice());
		}
		if (busDto.getMaxSeats() != 0) {
			bus.get().setMaxSeats(busDto.getMaxSeats());
		}

		Bus saveUpdateBus = busRepository.saveAndFlush(bus.get());

		return BusDto.of(saveUpdateBus);
	}

}
