<?php

namespace App\Http\Controllers;

use App\Models\Item;
use App\Models\Label;
use Carbon\Carbon;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;

class ItemController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        return view('items.index', [
            'items' => Item::orderBy('obtained', 'desc')->paginate(6),
            'labelsForEditing' => Label::all(),
            'labels' => Label::where('display', true)->get()
        ]);
    }

    /**
     * Show the form for creating a new resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function create()
    {
        return view('items.create', [
            'labels' => Label::where('display', true)->get()
        ]);
    }

    /**
     * Store a newly created resource in storage.
     *
     * @param \Illuminate\Http\Request $request
     * @return \Illuminate\Http\Response
     */
    public function store(Request $request)
    {

        if (!Auth::user()->is_admin) {
            return abort(403);
        }

        $validated = $request->validate([
                'name' => 'required|min:2',
                'description' => 'required|min:2',
                'labels' => 'nullable',
                'labels.*' => 'integer|distinct|exists:labels,id',
                'image' => 'nullable|image'
            ]
        );

        $validated['obtained'] = Carbon::now();

        if ($request->file('image')) {
            $file = $request->file('image');
            $filename = date('YmdHi') . $file->getClientOriginalName();
            $file->move(public_path('image'), $filename);
            $validated['image'] = $filename;
        }


        $i = Item::create($validated);
        $i->labels()->sync($request->labels);


        return redirect()->route('home');
    }

    /**
     * Display the specified resource.
     *
     * @param \App\Models\Item $item
     * @return \Illuminate\Http\Response
     */
    public function show(Item $item)
    {
        return view('items.show', [
            'item' => $item,
            'labels' => $item->labels()->where('display', true)->get(),
            'comments' => $item->comments()->orderBy('created_at', 'desc')->getEager()
        ]);
    }

    /**
     * Show the form for editing the specified resource.
     *
     * @param \App\Models\Item $item
     * @return \Illuminate\Http\Response
     */
    public function edit(Item $item)
    {
        return view('items.edit', [
            'item' => $item,
            'itemLabels' => $item->labels()->get(),
            'labels' => Label::all()
        ]);
    }

    /**
     * Update the specified resource in storage.
     *
     * @param \Illuminate\Http\Request $request
     * @param \App\Models\Item $item
     * @return \Illuminate\Http\Response
     */
    public function update(Request $request, Item $item)
    {
        if (!Auth::user()->is_admin) {
            return abort(403);
        }

        $validated = $request->validate([
                'name' => 'required|min:2',
                'description' => 'required|min:2',
                'labels' => 'nullable',
                'labels.*' => 'integer|distinct|exists:labels,id',
                'image' => 'nullable'
            ]
        );

        $validated['obtained'] = Carbon::now();

        if ($request->file('image')) {
            $file = $request->file('image');
            $filename = date('YmdHi') . $file->getClientOriginalName();
            $file->move(public_path('image'), $filename);
            $validated['image'] = $filename;
        }


        $item->update($validated);
        $item->labels()->sync($request->labels);


        return redirect()->route('items.show', $item->id);
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param \App\Models\Item $item
     * @return \Illuminate\Http\Response
     */
    public function destroy(Item $item)
    {
        if (Auth::user()->is_admin) {
            $item->delete();
        }

        return redirect()->route('home');
    }
}
