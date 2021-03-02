function validateDates(name, introduced, discontinued, companyID) {

	return isValidName(name) &&
			isValidIntroduced(introduced) &&
			isValidDiscontinued(discontinued) &&
			compareDates(introduced, discontinued) &&
			isValidCompany(companyID);
}

function isValidName(name) {
	var message = document.getElementById("computerName-validation-message");
	if((typeof name === "string") && name.trim().length > 0) {
		message.innerHTML = "";
		return true;
	} else {
		message.innerHTML = "Name is empty";
		return false;
	}
}

function isValidIntroduced(date) {
	var message = document.getElementById("introduced-validation-message");
	if (!date || isValidDate(date)) {
		message.innerHTML = "";
		return true;
	} else {
		message.innerHTML = "Invalid date format";
		return false;
	}
}

function isValidDiscontinued(date) {
	var message = document.getElementById("discontinued-validation-message");
	if (!date || isValidDate(date)) {
		message.innerHTML = "";
		return true;
	} else {
		message.innerHTML = "Invalid date format";
		return false;
	}
}

function compareDates(introduced, discontinued) {
	var message = document.getElementById("dateComparison-validation-message");
	if (!introduced || !discontinued || Date.parse(introduced) <= Date.parse(discontinued)) {
		message.innerHTML = "";
		return true;
	} else {
		message.innerHTML = "Discontinued is before introduced";
		return false;
	}
}

function isValidCompany(company) {
	var message = document.getElementById("companyId-validation-message");
	console.log(company);
	if (company > 0) {
		message.innerHTML = "";
		return true;
	} else {
		message.innerHTML = "None company selected";
		return false;
	}
}


function isValidDate(day) {
	var bitsDays = day.split('-');
	var yDays = bitsDays[0],
		mDays = bitsDays[1],
		dDays = bitsDays[2];
	var daysInMonth = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
	if (yDays % 400 === 0 || (yDays % 100 !== 0 && yDays % 4 === 0)) {
		daysInMonth[1] = 29;
	}
	return dDays <= daysInMonth[--mDays];
}

function isValidCompany(company) {
	return company>0;
}