<?php

// Based on https://github.com/paulgb/simplediff/blob/master/php/simplediff.php
function string_diff($old_str, $new_str) {
  $old = explode(" ", $old_str);
  $new = explode(" ", $new_str);

  function diff($old, $new){
    $matrix = [];
    $maxlen = 0;
    foreach ($old as $oindex => $ovalue){
      $nkeys = array_keys($new, $ovalue);
      foreach ($nkeys as $nindex){
        $matrix[$oindex][$nindex] = isset($matrix[$oindex - 1][$nindex - 1]) ?
        $matrix[$oindex - 1][$nindex - 1] + 1 : 1;
        if($matrix[$oindex][$nindex] > $maxlen){
          $maxlen = $matrix[$oindex][$nindex];
          $omax = $oindex + 1 - $maxlen;
          $nmax = $nindex + 1 - $maxlen;
        }
      }   
    }
    if ($maxlen == 0) return [["del" => implode(" ", $old), "ins" => implode(" ", $new)]];
    return array_merge(
      diff(array_slice($old, 0, $omax), array_slice($new, 0, $nmax)),
      array_slice($new, $nmax, $maxlen),
      diff(array_slice($old, $omax + $maxlen), array_slice($new, $nmax + $maxlen))
    );
  }

  $result = diff($old, $new);
  array_shift($result);
  return $result;
}