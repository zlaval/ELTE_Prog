﻿// GLEW
#include <GL/glew.h>

// SDL
#include <SDL.h>
#include <SDL_opengl.h>

// ImGui
#include <imgui/imgui.h>
#include <imgui/imgui_impl_sdl_gl3.h>

// standard
#include <iostream>
#include <sstream>

#include "Includes/GLDebugMessageCallback.h"

#include "MyApp.h"

int main( int argc, char* args[] )
{
	// állítsuk be, hogy kilépés előtt hívja meg a rendszer az exitProgram() függvényt - Kérdés: mi lenne enélkül?
	atexit([]() {
		SDL_Quit();

		std::cout << "Press any key to exit..." << std::endl;
		std::cin.get();
	});

	//
	// 1. lépés: inicializáljuk az SDL-t
	//

	// a grafikus alrendszert kapcsoljuk csak be, ha gond van, akkor jelezzük és lépjün ki
	if ( SDL_Init( SDL_INIT_VIDEO ) == -1 )
	{
		// irjuk ki a hibat es terminaljon a program
		std::cout << "[SDL init] Error while initializing SDL: " << SDL_GetError() << std::endl;
		return 1;
	}
			
	//
	// 2. lépés: állítsuk be az OpenGL-es igényeinket, hozzuk létre az ablakunkat, indítsuk el az OpenGL-t
	//

	// 2a: OpenGL indításának konfigurálása, ezt az ablak létrehozása előtt kell megtenni!
	SDL_GL_SetAttribute(SDL_GL_CONTEXT_PROFILE_MASK, SDL_GL_CONTEXT_PROFILE_CORE);
#ifdef _DEBUG
	// ha debug módú a fordítás, legyen az OpenGL context is debug módban, ekkor működik a debug callback
	SDL_GL_SetAttribute(SDL_GL_CONTEXT_FLAGS, SDL_GL_CONTEXT_DEBUG_FLAG);
#endif

	// állítsuk be, hogy hány biten szeretnénk tárolni a piros, zöld, kék és átlátszatlansági információkat pixelenként
	SDL_GL_SetAttribute(SDL_GL_BUFFER_SIZE,         32);
	SDL_GL_SetAttribute(SDL_GL_RED_SIZE,            8);
	SDL_GL_SetAttribute(SDL_GL_GREEN_SIZE,          8);
	SDL_GL_SetAttribute(SDL_GL_BLUE_SIZE,           8);
	SDL_GL_SetAttribute(SDL_GL_ALPHA_SIZE,          8);
	// duplapufferelés
	SDL_GL_SetAttribute(SDL_GL_DOUBLEBUFFER,		1);
	// mélységi puffer hány bites legyen
	SDL_GL_SetAttribute(SDL_GL_DEPTH_SIZE,          24);

	// antialiasing - ha kell
	//SDL_GL_SetAttribute(SDL_GL_MULTISAMPLEBUFFERS,  1);
	//SDL_GL_SetAttribute(SDL_GL_MULTISAMPLESAMPLES,  2);

	// hozzuk létre az ablakunkat
	SDL_Window *win = 0;
	win = SDL_CreateWindow( "Hello SDL&OpenGL!",		// az ablak fejléce
							100,						// az ablak bal-felső sarkának kezdeti X koordinátája
							100,						// az ablak bal-felső sarkának kezdeti Y koordinátája
							640,						// ablak szélessége
							480,						// és magassága
							SDL_WINDOW_OPENGL | SDL_WINDOW_SHOWN | SDL_WINDOW_RESIZABLE);			// megjelenítési tulajdonságok


	// ha nem sikerült létrehozni az ablakot, akkor írjuk ki a hibát, amit kaptunk és lépjünk ki
	if (win == 0)
	{
		std::cout << "[Window creation] Error while initializing SDL: " << SDL_GetError() << std::endl;
		return 1;
	}

	//
	// 3. lépés: hozzunk létre az OpenGL context-et - ezen keresztül fogunk rajzolni
	//

	SDL_GLContext	context	= SDL_GL_CreateContext(win);
	if (context == 0)
	{
		std::cout << "[OGL context creation] Error while initializing SDL" << SDL_GetError() << std::endl;
		return 1;
	}

	// megjelenítés: várjuk be a vsync-et
	SDL_GL_SetSwapInterval(1);

	// indítsuk el a GLEW-t
	GLenum error = glewInit();
	if ( error != GLEW_OK )
	{
		std::cout << "[GLEW] Error while init!" << std::endl;
		return 1;
	}

	// kérdezzük le az OpenGL verziót
	int glVersion[2] = {-1, -1}; 
	glGetIntegerv(GL_MAJOR_VERSION, &glVersion[0]); 
	glGetIntegerv(GL_MINOR_VERSION, &glVersion[1]); 
	std::cout << "Running OpenGL " << glVersion[0] << "." << glVersion[1] << std::endl;

	if ( glVersion[0] == -1 && glVersion[1] == -1 )
	{
		SDL_GL_DeleteContext(context);
		SDL_DestroyWindow( win );

		std::cout << "[OGL context creation] Could not create OGL context! SDL_GL_SetAttribute(...) calls may have wrong settings." << std::endl;

		return 1;
	}

	std::stringstream window_title;
	window_title << "OpenGL " << glVersion[0] << "." << glVersion[1];
	SDL_SetWindowTitle(win, window_title.str().c_str());

	// engedélyezzük és állítsuk be a debug callback függvényt ha debug context-ben vagyunk
	GLint context_flags;
	glGetIntegerv(GL_CONTEXT_FLAGS, &context_flags);
	if (context_flags & GL_CONTEXT_FLAG_DEBUG_BIT) {
		glEnable(GL_DEBUG_OUTPUT);
		glEnable(GL_DEBUG_OUTPUT_SYNCHRONOUS);
		glDebugMessageControl(GL_DONT_CARE, GL_DONT_CARE, GL_DEBUG_SEVERITY_NOTIFICATION, 0, nullptr, GL_FALSE);
		glDebugMessageCallback(GLDebugMessageCallback, nullptr);
	}

	//Imgui init
	ImGui_ImplSdlGL3_Init(win);

	//
	// 4. lépés: indítsuk el a fő üzenetfeldolgozó ciklust
	// 
	{
		// véget kell-e érjen a program futása?
		bool quit = false;
		// feldolgozandó üzenet ide kerül
		SDL_Event ev;

		// alkalmazas példánya
		CMyApp app;
		if (!app.Init())
		{
			SDL_GL_DeleteContext(context);
			SDL_DestroyWindow(win);
			std::cout << "[app.Init] Error while app init!" << std::endl;
			return 1;
		}
		int w, h;
		SDL_GetWindowSize(win, &w, &h);
		app.Resize(w, h);

		while (!quit)
		{
			// amíg van feldolgozandó üzenet dolgozzuk fel mindet:
			while (SDL_PollEvent(&ev))
			{
				ImGui_ImplSdlGL3_ProcessEvent(&ev);
				bool is_mouse_captured = ImGui::GetIO().WantCaptureMouse; //kell-e az imgui-nak az egér
				bool is_keyboard_captured = ImGui::GetIO().WantCaptureKeyboard;	//kell-e az imgui-nak a billentyűzet
				switch (ev.type)
				{
				case SDL_QUIT:
					quit = true;
					break;
				case SDL_KEYDOWN:
					if (ev.key.keysym.sym == SDLK_ESCAPE)
						quit = true;
					if (!is_keyboard_captured)
						app.KeyboardDown(ev.key);
					break;
				case SDL_KEYUP:
					if (!is_keyboard_captured)
						app.KeyboardUp(ev.key);
					break;
				case SDL_MOUSEBUTTONDOWN:
					if (!is_mouse_captured)
						app.MouseDown(ev.button);
					break;
				case SDL_MOUSEBUTTONUP:
					if (!is_mouse_captured)
						app.MouseUp(ev.button);
					break;
				case SDL_MOUSEWHEEL:
					if (!is_mouse_captured)
						app.MouseWheel(ev.wheel);
					break;
				case SDL_MOUSEMOTION:
					if (!is_mouse_captured)
						app.MouseMove(ev.motion);
					break;
				case SDL_WINDOWEVENT:
					if (ev.window.event == SDL_WINDOWEVENT_SIZE_CHANGED)
					{
						app.Resize(ev.window.data1, ev.window.data2);
					}
					break;
				}

			}
			ImGui_ImplSdlGL3_NewFrame(win); //Ezután lehet imgui parancsokat hívni, egészen az ImGui::Render()-ig

			app.Update();
			app.Render();
			ImGui::Render();

			SDL_GL_SwapWindow(win);
		}

		// takarítson el maga után az objektumunk
		app.Clean();
	}	// így az app destruktora még úgy fut le, hogy él a contextünk => a GPU erőforrásokat befoglaló osztályok destruktorai is itt futnak le

	//
	// 5. lépés: lépjünk ki
	// 
	ImGui_ImplSdlGL3_Shutdown();
	SDL_GL_DeleteContext(context);
	SDL_DestroyWindow( win );

	return 0;
}