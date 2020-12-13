<?php

session_start();

if (empty($_SESSION["binNum"])) {
    $_SESSION["binNum"] = "";
}

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $event = $_POST['event'];
    $value = $_SESSION["binNum"];
    if ($event == 'one') {
        $value = "1" . $value;
    } else if ($event == 'zero') {
        $value = "0" . $value;
    } else if ($event == 'reset') {
        session_unset();
        $value = "";
    }
    $_SESSION["binNum"] = $value;
    $result = $value . " = " . bindec($value);
    echo $result;
    exit;
}

?>

<div>
    <button id="b1">1</button>
    <button id="b0">0</button>
    <button id="br">reset</button>
</div>
<output></output>

<script>
    const b1 = document.querySelector('#b1')
    const b0 = document.querySelector('#b0')
    const br = document.querySelector('#br')
    const output = document.querySelector('output')

    addListener(b1, 'one')
    addListener(b0, 'zero')
    addListener(br, 'reset')

    function addListener(button, func) {
        button.addEventListener('click', async function (event) {
            event.preventDefault()
            const formData = new URLSearchParams()
            formData.append("event", func)
            const response = await fetch('index.php', {
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                },
                method: 'POST',
                body: formData
            })
            const value = await response.text()
            console.log(value)
            output.innerHTML = value
        })
    }
</script>


