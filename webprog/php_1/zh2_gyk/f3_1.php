<?php

$entries = read_contents("entry.json");

function read_contents($filename) {
    $raw_content = file_get_contents($filename);
    return json_decode($raw_content, TRUE);
}

function write_contents($filename, $contents) {
    file_put_contents($filename, json_encode($contents));
    return $contents;
}

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $event = $_POST['event'];

    if ($event == 'rename') {
        $index = $_POST['index'];
        $entries [$index]['name'] = $_POST['value'];
        write_contents("entry.json", $entries);

    } elseif ($event == 'delete') {
        $index = $_POST['index'];
        $value = $_POST['value'];

        $entries[$index]['tags'] = array_diff($entries[$index]['tags'], array($value));
        write_contents("entry.json", $entries);
    }

    exit;
}

?>

<?php if (count($entries) > 0): ?>
    <ul id="items">
        <?php foreach ($entries as $key => $entry): ?>
            <li id="name_<?= $key ?>"><?= $entry['name'] ?></li>
            <?php if (count($entry['tags']) > 0): ?>
                <ul>
                    <?php foreach ($entry['tags'] as $tag): ?>
                        <li class="item" index="<?= $key ?>"><?= $tag ?></li>
                    <?php endforeach ?>
                </ul>
            <?php endif ?>
        <?php endforeach ?>
    </ul>
<?php endif ?>

<?php if (count($entries) > 0): ?>
    <?php foreach ($entries as $key => $entry): ?>
        <input type="text" index="<?= $key ?>" id="id_<?= $key ?>" value="<?= $entry['name'] ?>">
        <button type="submit" for="id_<?= $key ?>">Save</button>
        </br></br>
    <?php endforeach ?>
<?php endif ?>


<script>
    const buttons = document.querySelectorAll("button")
    const items = document.querySelector("#items")

    buttons.forEach(function (btn) {
        btn.addEventListener('click', event => onRenameField(event, btn))
    })

    delegate(items, 'click', '.item', onRemoveTag)

    async function onRemoveTag(event) {
        const target = event.target
        const parent = target.parentElement
        const value = target.innerHTML
        const index = target.getAttribute("index")

        const formData = new URLSearchParams()
        formData.append("event", "delete")
        formData.append("value", value)
        formData.append("index", index)

        const response = await fetch('f3_1.php', {
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            method: 'POST',
            body: formData
        })

        await response;
        parent.removeChild(target)
    }

    async function onRenameField(event, btn) {
        event.preventDefault()
        const id = btn.getAttribute("for")
        const textField = document.querySelector(`#${id}`)
        const index = textField.getAttribute("index")
        const value = textField.value
        const formData = new URLSearchParams()
        formData.append("event", "rename")
        formData.append("value", value)
        formData.append("index", index)

        const response = await fetch('f3_1.php', {
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            method: 'POST',
            body: formData
        })
        await response
        const listItem = document.querySelector(`#name_${index}`)
        listItem.innerHTML = value
    }

    function delegate(parent, type, selector, handler) {
        parent.addEventListener(type, function (event) {
            const targetElement = event.target.closest(selector);
            if (this.contains(targetElement)) {
                handler.call(targetElement, event);
            }
        })
    }

</script>