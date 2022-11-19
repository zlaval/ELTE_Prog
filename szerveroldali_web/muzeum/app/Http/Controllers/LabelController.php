<?php

namespace App\Http\Controllers;

use App\Models\Label;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;
use Illuminate\Support\Facades\Session;

class LabelController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        //
    }

    /**
     * Show the form for creating a new resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function create()
    {
        return view('labels.create');
    }

    /**
     * Store a newly created resource in storage.
     *
     * @param \Illuminate\Http\Request $request
     * @return \Illuminate\Http\Response
     */
    public function store(Request $request)
    {
        if(!Auth::user()->is_admin){
            return abort(403);
        }

        $validated = $request->validate(
            [
                'name' => 'required|min:3|unique:labels',
                'color' => 'required|regex:/^#([0-9a-f]{8})$/i'
            ]
        );

        if ($request->has('display')) {
            $validated['display'] = $request['display'];
        } else {
            $validated['display'] = '0';
        }


        $l = Label::create($validated);
        Session::flash('label-created', $l->name);
        return redirect()->route('home');
    }

    /**
     * Display the specified resource.
     *
     * @param \App\Models\Label $label
     * @return \Illuminate\Http\Response
     */
    public function show(Label $label)
    {
        return view('labels.show', [
            'label' => $label,
            'items' => $label -> items() -> get()
        ]);
    }

    /**
     * Show the form for editing the specified resource.
     *
     * @param \App\Models\Label $label
     * @return \Illuminate\Http\Response
     */
    public function edit(Label $label)
    {
        return view('labels.edit', [
            'label' => $label
        ]);
    }

    /**
     * Update the specified resource in storage.
     *
     * @param \Illuminate\Http\Request $request
     * @param \App\Models\Label $label
     * @return \Illuminate\Http\Response
     */
    public function update(Request $request, Label $label)
    {

        if(!Auth::user()->is_admin){
            return abort(403);
        }

        $validated = $request->validate(
            [
                'name' => 'required|min:3',
                'color' => 'required|regex:/^#([0-9a-f]{8})$/i'
            ]
        );

        if ($request->has('display')) {
            $validated['display'] = '1';
        } else {
            $validated['display'] = '0';
        }

        $label->update($validated);
        return redirect()->route('home');
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param \App\Models\Label $label
     * @return \Illuminate\Http\Response
     */
    public function destroy(Label $label)
    {

        if (Auth::user()->is_admin) {
            $label->delete();
        }
        return redirect()->route('home');
    }
}
