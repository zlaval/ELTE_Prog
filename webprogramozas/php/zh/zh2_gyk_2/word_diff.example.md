# Usage: 

```php
word_diff(
  "Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet.",
  "Lorem dolor sit amet. Lorem ipsum dolor mit."
);
```

# Output: 

```php
array (size=9)
  0 => string 'Lorem' (length=5)
  1 => 
    array (size=2)
      'del' => 
        array (size=1)
          0 => string 'ipsum' (length=5)
      'ins' => 
        array (size=0)
          empty
  2 => string 'dolor' (length=5)
  3 => string 'sit' (length=3)
  4 => string 'amet.' (length=5)
  5 => string 'Lorem' (length=5)
  6 => string 'ipsum' (length=5)
  7 => string 'dolor' (length=5)
  8 => 
    array (size=2)
      'del' => 
        array (size=2)
          0 => string 'sit' (length=3)
          1 => string 'amet.' (length=5)
      'ins' => 
        array (size=1)
          0 => string 'mit.' (length=4)
```

# Formatted output:

```html
Lorem <del>ipsum</del><ins></ins> dolor sit amet. Lorem ipsum dolor <del>sit amet.</del><ins>mit.</ins>
```