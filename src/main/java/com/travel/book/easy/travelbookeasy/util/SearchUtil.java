package com.travel.book.easy.travelbookeasy.util;

import com.travel.book.easy.travelbookeasy.api.dto.SearchFilterDto;

public class SearchUtil {

	public static boolean checkAllFilter(SearchFilterDto searchFilterDto) {

		if (!searchFilterDto.getLocationFrom().equals("")
				&& !searchFilterDto.getLocationTo().equals("")
				&& searchFilterDto.getDate() != null 
				&& searchFilterDto.getSortByPrice() == true
				&& searchFilterDto.getSortByRating() == true) {
			return true;
		}

		return false;
	}

	public static boolean checkFitlerWitPriceWithoutRating(SearchFilterDto searchFilterDto) {

		if (!searchFilterDto.getLocationFrom().equals("")
				&& !searchFilterDto.getLocationTo().equals("")
				&& searchFilterDto.getDate() != null 
				&& searchFilterDto.getSortByPrice() == true
				&& searchFilterDto.getSortByRating() == false) {
			return true;
		}
		return false;
	}

	public static boolean checkFilterWithRatingWithoutPrice(SearchFilterDto searchFilterDto) {
		if (!searchFilterDto.getLocationFrom().equals("")
				&& !searchFilterDto.getLocationTo().equals("")
				&& searchFilterDto.getDate() != null 
				&& searchFilterDto.getSortByPrice() == false
				&& searchFilterDto.getSortByRating() == true) {
			return true;
		}

		return false;
	}

	public static boolean checkFilterWithPriceAndRatingWithoutDate(SearchFilterDto searchFilterDto) {

		if (!searchFilterDto.getLocationFrom().equals("")
				&& !searchFilterDto.getLocationTo().equals("")
				&& searchFilterDto.getDate() == null
				&& searchFilterDto.getSortByPrice() == true
				&& searchFilterDto.getSortByRating() == true) {
			return true;
		}

		return false;
	}

	public static boolean checkFilterWithPriceWithoutDateAndRating(SearchFilterDto searchFilterDto) {
		if (!searchFilterDto.getLocationFrom().equals("")
				&& !searchFilterDto.getLocationTo().equals("")
				&& searchFilterDto.getDate() == null
				&& searchFilterDto.getSortByPrice() == true
				&& searchFilterDto.getSortByRating() == false) {
			return true;
		}

		return false;

	}

	public static boolean checkFilterWithRatingWithoutDateAndPrice(SearchFilterDto searchFilterDto) {

		if (!searchFilterDto.getLocationFrom().equals("")
				&& !searchFilterDto.getLocationTo().equals("")
				&& searchFilterDto.getDate() == null
				&& searchFilterDto.getSortByPrice() == false
				&& searchFilterDto.getSortByRating() == true) {
			return true;
		}

		return false;

	}
	public static boolean checkoFilterOnlyWithPrice(SearchFilterDto searchFilterDto){

		if (searchFilterDto.getLocationFrom().equals("")
				&& searchFilterDto.getLocationTo().equals("")
				&& searchFilterDto.getDate() == null
				&& searchFilterDto.getSortByPrice() == true
				&& searchFilterDto.getSortByRating() == false) {
			return true;
		}

		return false;
	}
	public static boolean checkFilterOnlyRating(SearchFilterDto searchFilterDto){
		
		if (searchFilterDto.getLocationFrom().equals("")
				&& searchFilterDto.getLocationTo().equals("")
				&& searchFilterDto.getDate() == null
				&& searchFilterDto.getSortByPrice() == false
				&& searchFilterDto.getSortByRating() == true) {
			return true;
		}

		return false;
	}
	public static boolean checkFilterOnlyLocation(SearchFilterDto searchFilterDto){
		if (!searchFilterDto.getLocationFrom().equals("")
				&& !searchFilterDto.getLocationTo().equals("")
				&& searchFilterDto.getDate() == null
				&& searchFilterDto.getSortByPrice() == false
				&& searchFilterDto.getSortByRating() == false) {
			return true;
		}

		return false;
	}
	public static boolean checkFilterLocationAndDateWithoutPriceAndRating(SearchFilterDto searchFilterDto){
		if (!searchFilterDto.getLocationFrom().equals("")
				&& !searchFilterDto.getLocationTo().equals("")
				&& searchFilterDto.getDate() != null
				&& searchFilterDto.getSortByPrice() == false
				&& searchFilterDto.getSortByRating() == false) {
			return true;
		}

		return false;
	}
}
