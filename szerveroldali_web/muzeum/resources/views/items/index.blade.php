<x-guest-layout>
    <div class="container mx-auto p-3 lg:px-36">
        @if (Route::has('login'))
            <div class="hidden fixed top-0 right-0 px-6 py-4 sm:block ">
                @auth
                    <a href="{{ route('logout') }}"
                       class="text-lg  underline bg-red-700 rounded px-2 py-1 text-white font-bold">Logout</a>
                @else
                    <a href="{{ route('login') }}"
                       class="font-bold text-lg bg-green-700 px-2 py-1 text-white underline rounded">Log in</a>
                @endauth
            </div>
        @endif

        <div class="grid grid-cols-1 lg:grid-cols-2 mb-4">
            <div>
                <h1 class="font-bold my-4 text-4xl">Múzeum</h1>
            </div>
            @auth
                @if(Auth::user()->is_admin)
                    <div class="flex items-center gap-2 lg:justify-end">
                        <a href="{{ route('labels.create') }}"
                           class="bg-green-500 hover:bg-green-700 px-2 py-1 text-white"><i
                                class="fas fa-plus-circle"></i> Új Cimke</a>
                        <a href="{{ route('items.create') }}"
                           class="bg-green-500 hover:bg-green-700 px-2 py-1 text-white"><i
                                class="fas fa-plus-circle"></i> Új Tárgy</a>
                    </div>
                @endif
            @endauth
        </div>
        <div class="col-span-4 lg:col-span-1">
            <div class="grid grid-cols-1 gap-3">
                @auth
                    @if(Auth::user()->is_admin)
                        <div class="border px-2.5 py-2 border-gray-400">
                            <h3 class="mb-0.5 text-xl font-semibold">
                                Címkék szerkesztése
                            </h3>
                            <div class="flex flex-row flex-wrap gap-1 mt-3">
                                @foreach($labelsForEditing as $l)
                                    <a href="{{ route('labels.edit', $l) }}" class="py-0.5 px-1.5 font-semibold text-sm"
                                       style="background-color: {{$l -> color}};">{{ $l -> name}}</a>
                                @endforeach
                            </div>
                        </div>
                    @endif
                @endauth
                <div class="border px-2.5 py-2 border-gray-400">
                    <h3 class="mb-0.5 text-xl font-semibold">
                        Szűrés cimkék alapján
                    </h3>
                    <div class="flex flex-row flex-wrap gap-1 mt-3">
                        @foreach($labels as $l)
                            <a href="{{ route('labels.show', $l) }}" class="py-0.5 px-1.5 font-semibold text-sm"
                               style="background-color: {{$l -> color}};">{{ $l -> name}}</a>
                        @endforeach
                    </div>
                </div>
            </div>
        </div>
        <div class="grid grid-cols-3 gap-6">
            <div class="col-span-3 lg:col-span-3">
                <h2 class="font-semibold text-3xl my-2">Tárgyak</h2>
                <div class="grid grid-cols-3 gap-3">
                    @if(Session::has('category-created'))
                        <div class="col-span-3 bg-green-200 text-center rounded-lg py-1">
                            A(z) {{ Session::get('label-created') }} címke létrejött!
                        </div>
                    @endif

                    @foreach ($items as $i)
                        <div class="col-span-3 lg:col-span-1 border border-gray-400">
                            @if($i->image)
                                <img class="object-cover w-full" src="{{ asset('image/'.$i->image) }}"
                                     style="height:400px;">
                            @else
                                <p class="bg-gray-400 w-full font-semibold" style="height:400px;">
                                    Nincs kép
                                </p>
                            @endif
                            <div class="px-2.5 py-2">
                                <h3 class="text-xl mb-0.5 font-semibold">
                                    {{ $i -> name }}
                                </h3>
                                {{--                                <h4 class="text-gray-400">--}}
                                {{--                                    <span class="mr-2"><i class="fas fa-user"></i>{{ $i -> author -> name }}</span>--}}
                                {{--                                </h4>--}}
                                <p class="text-gray-600 mt-1 h-20">
                                    {{ Str::limit($i -> description, 100) }}
                                </p>
                                <a href="{{ route('items.show', $i)}}"
                                   class="bg-blue-500 hover:bg-blue-600 px-1.5 py-1 text-white mt-3 font-semibold my-3"><i
                                        class="fas fa-angle-right"></i> Részletek</a>
                            </div>
                        </div>
                    @endforeach

                </div>
                <div class="flex justify-start my-5">
                    {{$items -> links()}}
                </div>
            </div>

        </div>
    </div>
</x-guest-layout>
