<?php

session_start();


function generateRandomDate() {
    $timestampFrom = strtotime("2000-01-01");
    $timestampTo = strtotime("2020-12-31");
    $timestamp = mt_rand($timestampFrom, $timestampTo);
    $_SESSION["randomDate"] = $timestamp;
}

function validateDateAttribute($input) {
    if (!$input || !isInputDateFormat($input)) {
        return false;
    }
    return true;
}

function isInputDateFormat($date) {
    $format = '';
    $dateFormat = DateTime::createFromFormat($format, $date);
    return $dateFormat && $dateFormat->format($format) == $date;
}


if (empty($_SESSION["randomDate"])) {
    generateRandomDate();
}

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $date = date('Y-m-d', $_SESSION["randomDate"]);
    $guess = $_POST['guess'];

    if (!validateDateAttribute($guess)) {
        http_response_code(300);
        echo json_encode(array("error" => "Invalid input format"));
    } else {
        http_response_code(200);
        if ($guess == $date) {
            session_unset();
            generateRandomDate();
            echo json_encode(array("guess" => "That's it!"));
        } else if ($guess < $date) {
            echo json_encode(array("guess" => "Later"));
        } else {
            echo json_encode(array("guess" => "Earlier"));
        }
    }

    exit;
}

?>

<form>
    <input type="date" name="date_guess">
    <button type="submit">Guess</button>
</form>
<output>Gondoltam egy dátumra</output>


<script>
    const submitBtn = document.querySelector("button")
    const dateInput = document.querySelector("input")
    const outPut = document.querySelector("output")

    submitBtn.addEventListener('click', async function (event) {
        event.preventDefault()
        const value = dateInput.value

        const formData = new URLSearchParams()
        formData.append("guess", value)
        const response = await fetch('f2.php', {
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            method: 'POST',
            body: formData
        })

        const status = response.status
        const result = await response.text()
        const resultJson=JSON.parse(result)
        if (status === 200) {
            outPut.innerHTML = resultJson['guess']
        } else {
            outPut.innerHTML = resultJson['error']
        }
    });


</script>
