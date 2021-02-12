const btnAdmin = document.querySelector("#btn-admin");
const calendar = document.querySelector(".booking-calendar-table-body")

const btnPrevMonth = document.querySelector("#btn-prev-month")
const btnNextMonth = document.querySelector("#btn-next-month")
const calendarMonthDiv = document.querySelector(".booking-month")

if (btnAdmin) {
    btnAdmin.addEventListener("click", event => {
        window.location.href = "../page/admin.php"
    })
}

const DAYS = {
    "Monday": 0,
    "Tuesday": 1,
    "Wednesday": 2,
    "Thursday": 3,
    "Friday": 4,
    "Saturday": 5,
    "Sunday": 6
}

btnPrevMonth.addEventListener("click", async event => {
    event.preventDefault()
    await getCalendarData("prev")
})

btnNextMonth.addEventListener("click", async event => {
    event.preventDefault()
    await getCalendarData("next")
})

async function getCalendarData(direction) {
    const searchParam = new URLSearchParams({
        direction: direction
    });
    const response = await fetch('../page/booking.php?' + searchParam, {
        method: 'GET',
    })
    const json=await response.json()
    renderCalendar(json)
}

function renderCalendar(data) {
    let html = "";

    const firstDay = data[0].dayName
    const calendatMonth = data[0].calendarMonth
    calendarMonthDiv.innerHTML = calendatMonth

    html += "</tr>"
    for (let i = 0; i < DAYS[firstDay]; ++i) {
        html += `<td class="booking-calendar-table-td"></td>`
    }

    let lastDay = firstDay;
    data.forEach(function (element) {
        lastDay = element.dayName
        html += `<td class="booking-calendar-table-td"><div class="booking-calendar-table-td-div"><span class="booking-date">${element.dayInMonth}</span>`

        for (let key of Object.keys(element.appointMents)) {
            const appointment = element.appointMents[key]
            const colorClass = appointment.freeSpaces === 0 ? " appointment-red " : " appointment-green "
            html += ` <a class="booking-appointment-link" href="../page/bookingdetail.php?appointmentId=${appointment.id}">
            <div class="booking-appointment text-center ${colorClass}">             
                ${appointment.time} óra (${appointment.reservationCount}/${appointment.maxCapacity} hely)              
            </div></a>`
        }


        html += `</div></td>`
        if (DAYS[lastDay] === 6) {
            html += "</tr><tr>"
        }
    });

    for (let i = DAYS[lastDay]; i < 7; ++i) {
        html += `<td class="booking-calendar-table-td"></td>`
    }
    html += "</tr>"


    calendar.innerHTML = html
}

document.onload = getCalendarData("actual")

