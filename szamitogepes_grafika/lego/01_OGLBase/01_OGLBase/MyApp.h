#pragma once

#include <memory>
#include <GL/glew.h>
#include <SDL.h>
#include <SDL_opengl.h>
#include <glm/glm.hpp>
#include <glm/gtc/matrix_transform.hpp>
#include <glm/gtx/transform2.hpp>
#include "includes/gCamera.h"
#include "includes/ProgramObject.h"
#include "includes/BufferObject.h"
#include "includes/VertexArrayObject.h"
#include "includes/TextureObject.h"
#include "includes/ObjParser_OGL3.h"


struct Muled {
	glm::vec3 first;
	glm::vec3 second;
};

class CMyApp
{
public:
	CMyApp();
	~CMyApp();

	bool init();
	void cleanUp();

	void update();
	void render();

	void keyboardDown(SDL_KeyboardEvent&);
	void keyboardUp(SDL_KeyboardEvent&);
	void mouseMove(SDL_MouseMotionEvent&);
	void mouseDown(SDL_MouseButtonEvent&);
	void mouseUp(SDL_MouseButtonEvent&);
	void mouseWheel(SDL_MouseWheelEvent&);
	void resizeWindow(int, int);
	

protected:
	static const int TRACK_N = 80;
	static const int CYLINDER_N = 80;
	static const int TRACK_SIZE = 600;
	static const int N = 40;
	static const int M = 20;

	ProgramObject		legoShader;
	ProgramObject		skyboxShader;
	ProgramObject       trackShader;
	ProgramObject       pointShader;

	VertexArrayObject	baseLegoVAO;
	IndexBuffer			baseLegoIndices;
	ArrayBuffer			baseLegoVertexBuffer;

	VertexArrayObject	torusVAO;
	IndexBuffer			torusIndices;
	ArrayBuffer			torusVertexBuffer;

	VertexArrayObject	sphereVAO;
	IndexBuffer			sphereIndices;
	ArrayBuffer			sphereVertexBuffer;

	VertexArrayObject	trackVAO;
	IndexBuffer			trackIndices;
	ArrayBuffer			trackVertexBuffer;

	VertexArrayObject	skyboxVao;
	IndexBuffer			skyboxIndices;
	ArrayBuffer			skyboxPos;

	gCamera				camera;

	Texture2D			trackTexture;
	TextureCubeMap		skyboxTexture;



	struct LegoBlock {
		glm::vec4 color = glm::vec4(0.3f, 0.3f, 0.9f, 1.f);
		glm::vec3 position = glm::vec3(0.f, 0.f, 0.f);
		bool slim = false;
		bool wheel = false;
		bool driver = false;
		int lengthElem = 1;
		int widthElem = 1;
	};

	struct Vertex
	{
		glm::vec3 p;
		glm::vec3 n;
		glm::vec2 t;
	};



	std::vector<LegoBlock> staticContent;
	std::vector<LegoBlock> legoCar;
	std::vector<glm::vec3>noCp;

	std::vector<glm::vec3> treePositions{
			{100,1,-110 },
			{100,1,-150 },
			{100,1,-180 },

			{80,1,-110 },
			{80,1,-150 },
			{80,1,-180 },

			//{60,1,-110 },
			//{60,1,-150 },
			//{60,1,-180 },

			{40,1,-110 },
			{40,1,-150 },
			{40,1,-180 },

			//{20,1,-110 },
			//{20,1,-150 },
			//{20,1,-180 },

			{0,1,-110 },
			{0,1,-150 },
			{0,1,-180 },


			{-40,1,-110 },
			{-40,1,-150 },
			{-40,1,-180 },
			
	};

	std::vector<glm::vec3> pyramPositions{
		{-150,1,-180 },
		//{150,1,100 },
	};

	std::vector<glm::vec3>	controlPoints{
		//{0,1,0 },
		//{0,10,0 },


		{0,1,-46.5 }, 	
		{-50,1,-46.5 }, 	
		{-100,1,-46.5 }, 		
		{-150,1,-46.5 }, 	 
		{-200,1,-46.5 }, 
		{-234,1,-46.5 },
		{-250,1,0} ,
		{-250,1,50},
		{-250,1,100},
		{-250,1,150},
		{-250,1,200},
		{-200,1,225} ,
		{-150,1,200},
		{-140,1,170},

		{-140,1,120},
		{-160,1,60},

		{-110,1,40},
		{-90,1,80},
		{-70,1,130},
		{-50,1,180},
		{0,1,180},
		{50,1,180},
		{60,1,120},
		{60,1,80},

		{90,1,40},
		{130,1,40},
		{150,1,90},
		{150,1,130},
		{150,1,170},
		{200,1,190},
		{250,1,170},

		{250,1,120},
		{250,1,70},
		{250,1,20},
		{250,1,-30},
		{250,1,-80},
		{250,1,-130},

		{250,1,-180},
		{200,1,-230},
		{150,1,-180},
		{150,1,-120},
		{150,1,-90},
		{130,1,-60},
		{80,1,-50},
		{40,1,-46},
		{0,1,-46},
	
	};


	std::vector<glm::vec3>	controlPointsSC1{
		//{0,1,0 },
		//{0,10,0 },

		{30,1,-40.5 },
		{0,1,-40.5 },
		{-50,1,-40.5 },
		{-100,1,-40.5 },
		{-150,1,-40.5 },
		{-200,1,-40.5 },
		{-234,1,-40.5 },
		{-260,1,0} ,
		{-260,1,50},
		{-260,1,100},
		{-250,1,150},
		{-250,1,200},

		{-200,1,250} ,
		{-160,1,200},
		{-160,1,170},

		{-160,1,120},
		{-160,1,80},

		{-110,1,60},
		{-90,1,70},
		{-70,1,130},
		{-50,1,180},
		{0,1,200},
		{30,1,200},
		{60,1,120},
		{60,1,80},

		{90,1,40},
		{130,1,40},
		{150,1,90},
		{150,1,130},
		{150,1,170},
		{200,1,190},
		{250,1,170},

		{220,1,120},
		{220,1,70},
		{220,1,20},
		{220,1,-30},
		{220,1,-80},
		{220,1,-110},

		{220,1,-180},
		{200,1,-230},
		{150,1,-180},
		{150,1,-120},
		{150,1,-90},
		{130,1,-40},
		{80,1,-40},
		{30,1,-40},

	};


	std::vector<LegoBlock> staticCar1;

	LegoBlock actualElement = LegoBlock{};
	float actualCol[4] = { 0.3,0.3,0.9,1.0 };
	float delta_time = 0;
	float car_time = 0.001;
	float static_car_time = 0.001;
	float move_speed = 0.0f;
	float cameraMoveTime = 0.0f;

	std::unique_ptr<Mesh> cylinderModel;

	bool buildPhase = true;
	bool camMovePhase = false;
	bool pointLight = false;
	bool rotateCamera = false;
	bool showstatic = false;
	bool blink = false;
	glm::vec3 startCameraPos;
	glm::vec3 carPos;
	glm::vec3 carDirection;

	void initShaders();
	void initBaseLego();
	void initSkyBox();
	void initTrack();
	void initTorus();
	void initSphere();
	void initRoutePoints();


	void drawLego(glm::mat4 viewProj, LegoBlock block, bool canmove,  float xPos, float zPos, std::vector<glm::vec3> controlPointVec, float time,  bool mainCar);
	void drawLegos(glm::mat4 viewProj);
	void drawTrack(glm::mat4 viewProj);
	void drawControllPoints();

	void drawDriver(LegoBlock block, glm::mat4 viewProj, glm::mat4  basePosition, float xPos, float zPos, bool canmove,float rotate);
	void drawLegoElement(LegoBlock block, glm::mat4 viewProj, glm::mat4  basePosition,  float xPos, float zPos, bool canmove, float rotate);
	void drawWheel(LegoBlock block, glm::mat4 viewProj, glm::mat4  basePosition,  float xPos, float zPos, bool canmove, float rotate);

	glm::vec3 getTrackNorm(float u, float v);
	glm::vec3 getTrackPos(float u, float v);
	glm::vec2 getTex(float u, float v);
	glm::vec3 getTorusPos(float u, float v);
	glm::vec3 getSpherePos(float u, float v);

	void addBase();

	void moveActual(SDL_KeyboardEvent& key);
	bool isOnElement();
	bool canMoveTo(glm::vec3 newPos);
	void buildElementIn();
	void setColor(float r, float g, float b);
	bool isBuildCorrect();
	void initBasicCars();

	void generateBase(std::vector<LegoBlock> &car, float tx, float tz, bool grey);
	void addStaticCarElements(std::vector<LegoBlock>& car, float tx, float tz);

	float lastMillis = 0.f;

	glm::vec3 eval(float t, std::vector<glm::vec3> controlPointVec);
	glm::vec3 catmull(float t,glm::vec3 p0, glm::vec3 p1, glm::vec3 p2, glm::vec3 p3);


	Muled getIntervalIndex(float t,  std::vector<glm::vec3> controlPointVec);

	void addStaticContent();
	
};

