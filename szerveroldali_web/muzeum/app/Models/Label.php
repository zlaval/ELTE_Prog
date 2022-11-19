<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Label extends Model
{
    use HasFactory;

    protected $fillable = ['name', 'display', 'color'];

    public function items()
    {
        return $this->belongsToMany(Item::class);
    }
}
