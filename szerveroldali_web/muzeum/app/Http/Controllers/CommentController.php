<?php

namespace App\Http\Controllers;

use App\Models\Comment;
use App\Models\Item;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;

class CommentController extends Controller
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
        //
    }

    /**
     * Store a newly created resource in storage.
     *
     * @param \Illuminate\Http\Request $request
     * @return \Illuminate\Http\Response
     */
    public function store(Request $request)
    {
        if(!Auth::user()){
            return abort(403);
        }

        $validated = $request->validate([
            'text' => 'required|min:1',
        ]);

        $item = Item::find($request['item_id']);
        $user = auth()->user();

        $validated['item_id'] = $item->id;
        $validated['user_id'] = $user->id;

        Comment::create($validated);

        return redirect()->route('items.show', $item->id);
    }

    /**
     * Display the specified resource.
     *
     * @param \App\Models\Comment $comment
     * @return \Illuminate\Http\Response
     */
    public function show(Comment $comment)
    {
        //
    }

    /**
     * Show the form for editing the specified resource.
     *
     * @param \App\Models\Comment $comment
     * @return \Illuminate\Http\Response
     */
    public function edit(Comment $comment)
    {
        if(!Auth::user()){
            return abort(403);
        }
        return view('comments.edit', [
            'comment' => $comment
        ]);
    }

    /**
     * Update the specified resource in storage.
     *
     * @param \Illuminate\Http\Request $request
     * @param \App\Models\Comment $comment
     * @return \Illuminate\Http\Response
     */
    public function update(Request $request, Comment $comment)
    {
        if(Auth::user()!=null && (Auth::user()->id == $comment->user->id || Auth::user()->is_admin  )){

            $validated = $request->validate([
                'text' => 'required|min:1',
            ]);

           $comment->update($validated);
        }


        return redirect()->route('items.show', $comment->item->id);
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param \App\Models\Comment $comment
     * @return \Illuminate\Http\Response
     */
    public function destroy(Comment $comment)
    {
        if(Auth::user()!=null && (Auth::user()->id == $comment->user->id || Auth::user()->is_admin  )){
            $comment->delete();
        }
        return redirect()->back();
    }
}
