#include "MyApp.h"

#include <math.h>
#include <vector>

#include <array>
#include <list>
#include <tuple>
#include <imgui/imgui.h>
#include "includes/GLUtils.hpp"

CMyApp::CMyApp(void)
{
	camera.SetView(glm::vec3(60, 50, 0), glm::vec3(0, 0, 0), glm::vec3(0, 1, 0));

}

CMyApp::~CMyApp(void)
{
}


glm::vec2  CMyApp::getTex(float u, float v) {
	return glm::vec2(u, 1.f - v);
}

glm::vec3 CMyApp::getTrackNorm(float u, float v) {
	return glm::vec3(0.f, 1.f, 0.f);
}

glm::vec3 CMyApp::getTrackPos(float u, float v) {
	return glm::vec3(u * (float)TRACK_SIZE - ((float)TRACK_SIZE / 2.f), 0.f, -v * (float)TRACK_SIZE + ((float)TRACK_SIZE / 2.f));
}

glm::vec3 CMyApp::getTorusPos(float u, float v)
{
	u *= 2 * 3.1415f;
	v *= 2 * 3.1415f;
	float r = 1;
	float R = 5;

	return glm::vec3(
		(R + r * cosf(u)) * cosf(v),
		r * sinf(u),
		(R + r * cosf(u)) * sinf(v)
	);
}

glm::vec3 CMyApp::getSpherePos(float u, float v)
{
	u *= 2 * 3.1415f;
	v *= 3.1415f;
	float r = 2;

	return glm::vec3(r * sin(v) * cos(u),
		r * cos(v),
		r * sin(v) * sin(u));
}

void  CMyApp::initSphere() {
	Vertex vert[(N + 1) * (M + 1)];
	for (int i = 0; i <= N; ++i)
		for (int j = 0; j <= M; ++j)
		{
			float u = i / (float)N;
			float v = j / (float)M;

			vert[i + j * (N + 1)].p = getSpherePos(u, v);
			vert[i + j * (N + 1)].n = glm::normalize(vert[i + j * (N + 1)].p);
			vert[i + j * (N + 1)].t = getTex(u, v);
		}

	GLushort indices[3 * 2 * (N) * (M)];
	for (int i = 0; i < N; ++i)
		for (int j = 0; j < M; ++j)
		{
			indices[6 * i + j * 3 * 2 * (N)+0] = (i)+(j) * (N + 1);
			indices[6 * i + j * 3 * 2 * (N)+1] = (i + 1) + (j) * (N + 1);
			indices[6 * i + j * 3 * 2 * (N)+2] = (i)+(j + 1) * (N + 1);
			indices[6 * i + j * 3 * 2 * (N)+3] = (i + 1) + (j) * (N + 1);
			indices[6 * i + j * 3 * 2 * (N)+4] = (i + 1) + (j + 1) * (N + 1);
			indices[6 * i + j * 3 * 2 * (N)+5] = (i)+(j + 1) * (N + 1);
		}

	sphereVertexBuffer.BufferData(vert);
	sphereIndices.BufferData(indices);
	sphereVAO.Init(
		{
			{ CreateAttribute<		0,
									glm::vec3,
									0,
									sizeof(Vertex)
								>, sphereVertexBuffer },
			{ CreateAttribute<1, glm::vec3, (sizeof(glm::vec3)), sizeof(Vertex)>, sphereVertexBuffer },
			{ CreateAttribute<2, glm::vec2, (2 * sizeof(glm::vec3)), sizeof(Vertex)>, sphereVertexBuffer },
		},
		sphereIndices
	);

}


void CMyApp::initTorus() {
	Vertex vert[(N + 1) * (M + 1)];
	for (int i = 0; i <= N; ++i)
		for (int j = 0; j <= M; ++j)
		{
			float u = i / (float)N;
			float v = j / (float)M;

			vert[i + j * (N + 1)].p = getTorusPos(u, v);
			vert[i + j * (N + 1)].n = glm::normalize(vert[i + j * (N + 1)].p);
			vert[i + j * (N + 1)].t = getTex(u, v);
		}

	GLushort indices[3 * 2 * (N) * (M)];
	for (int i = 0; i < N; ++i)
		for (int j = 0; j < M; ++j)
		{
			indices[6 * i + j * 3 * 2 * (N)+0] = (i)+(j) * (N + 1);
			indices[6 * i + j * 3 * 2 * (N)+1] = (i + 1) + (j) * (N + 1);
			indices[6 * i + j * 3 * 2 * (N)+2] = (i)+(j + 1) * (N + 1);
			indices[6 * i + j * 3 * 2 * (N)+3] = (i + 1) + (j) * (N + 1);
			indices[6 * i + j * 3 * 2 * (N)+4] = (i + 1) + (j + 1) * (N + 1);
			indices[6 * i + j * 3 * 2 * (N)+5] = (i)+(j + 1) * (N + 1);
		}

	torusVertexBuffer.BufferData(vert);
	torusIndices.BufferData(indices);
	torusVAO.Init(
		{
			{ CreateAttribute<		0,
									glm::vec3,
									0,
									sizeof(Vertex)
								>, torusVertexBuffer },
			{ CreateAttribute<1, glm::vec3, (sizeof(glm::vec3)), sizeof(Vertex)>, torusVertexBuffer },
			{ CreateAttribute<2, glm::vec2, (2 * sizeof(glm::vec3)), sizeof(Vertex)>, torusVertexBuffer },
		},
		torusIndices
	);
}


void CMyApp::initTrack() {

	Vertex vert[(TRACK_N + 1) * (TRACK_N + 1)];

	for (int i = 0; i <= TRACK_N; ++i)
		for (int j = 0; j <= TRACK_N; ++j)
		{
			float u = i / (float)TRACK_N;
			float v = j / (float)TRACK_N;

			vert[i + j * (TRACK_N + 1)].p = getTrackPos(u, v);
			vert[i + j * (TRACK_N + 1)].n = getTrackNorm(u, v);
			vert[i + j * (TRACK_N + 1)].t = getTex(u, v);
		}

	GLushort indices[3 * 2 * (TRACK_N) * (TRACK_N)];
	for (int i = 0; i < TRACK_N; ++i)
		for (int j = 0; j < TRACK_N; ++j)
		{

			indices[6 * i + j * 3 * 2 * (TRACK_N)+0] = (i)+(j) * (TRACK_N + 1);
			indices[6 * i + j * 3 * 2 * (TRACK_N)+1] = (i + 1) + (j) * (TRACK_N + 1);
			indices[6 * i + j * 3 * 2 * (TRACK_N)+2] = (i)+(j + 1) * (TRACK_N + 1);
			indices[6 * i + j * 3 * 2 * (TRACK_N)+3] = (i + 1) + (j) * (TRACK_N + 1);
			indices[6 * i + j * 3 * 2 * (TRACK_N)+4] = (i + 1) + (j + 1) * (TRACK_N + 1);
			indices[6 * i + j * 3 * 2 * (TRACK_N)+5] = (i)+(j + 1) * (TRACK_N + 1);
		}


	trackVertexBuffer.BufferData(vert);
	trackIndices.BufferData(indices);
	trackVAO.Init(
		{
			{ CreateAttribute<		0,
									glm::vec3,
									0,
									sizeof(Vertex)
								>, trackVertexBuffer },
			{ CreateAttribute<1, glm::vec3, (sizeof(glm::vec3)), sizeof(Vertex)>, trackVertexBuffer },
			{ CreateAttribute<2, glm::vec2, (2 * sizeof(glm::vec3)), sizeof(Vertex)>, trackVertexBuffer },
		},
		trackIndices
	);


}

void CMyApp::initBaseLego()
{
	//struct Vertex{ glm::vec3 position; glm::vec3 normals; glm::vec2 texture; };
	std::vector<Vertex>vertices;

	//front									 
	vertices.push_back({ glm::vec3(-1.5, 0, +1.5), glm::vec3(0, 0, 1), glm::vec2(0, 0) });
	vertices.push_back({ glm::vec3(+1.5, 0, +1.5), glm::vec3(0, 0, 1), glm::vec2(1, 0) });
	vertices.push_back({ glm::vec3(-1.5, +1, +1.5), glm::vec3(0, 0, 1), glm::vec2(0, 1) });
	vertices.push_back({ glm::vec3(+1.5, +1, +1.5), glm::vec3(0, 0, 1), glm::vec2(1, 1) });
	//back
	vertices.push_back({ glm::vec3(+1.5, -0, -1.5), glm::vec3(0, 0, -1), glm::vec2(0, 0) });
	vertices.push_back({ glm::vec3(-1.5, 0, -1.5), glm::vec3(0, 0, -1), glm::vec2(1, 0) });
	vertices.push_back({ glm::vec3(+1.5, +1, -1.5), glm::vec3(0, 0, -1), glm::vec2(0, 1) });
	vertices.push_back({ glm::vec3(-1.5, +1, -1.5), glm::vec3(0, 0, -1), glm::vec2(1, 1) });
	//right									 
	vertices.push_back({ glm::vec3(+1.5, 0, +1.5), glm::vec3(1, 0, 0), glm::vec2(0, 0) });
	vertices.push_back({ glm::vec3(+1.5, 0, -1.5), glm::vec3(1, 0, 0), glm::vec2(1, 0) });
	vertices.push_back({ glm::vec3(+1.5, 1, +1.5), glm::vec3(1, 0, 0), glm::vec2(0, 1) });
	vertices.push_back({ glm::vec3(+1.5, 1, -1.5), glm::vec3(1, 0, 0), glm::vec2(1, 1) });
	//left									 
	vertices.push_back({ glm::vec3(-1.5, 0, -1.5), glm::vec3(-1, 0, 0), glm::vec2(0, 0) });
	vertices.push_back({ glm::vec3(-1.5, 0, +1.5), glm::vec3(-1, 0, 0), glm::vec2(1, 0) });
	vertices.push_back({ glm::vec3(-1.5, 1, -1.5), glm::vec3(-1, 0, 0), glm::vec2(0, 1) });
	vertices.push_back({ glm::vec3(-1.5, 1, +1.5), glm::vec3(-1, 0, 0), glm::vec2(1, 1) });
	//top									 
	vertices.push_back({ glm::vec3(-1.5, 1, +1.5), glm::vec3(0, 1, 0), glm::vec2(0, 0) });
	vertices.push_back({ glm::vec3(+1.5, 1, +1.5), glm::vec3(0, 1, 0), glm::vec2(1, 0) });
	vertices.push_back({ glm::vec3(-1.5, 1, -1.5), glm::vec3(0, 1, 0), glm::vec2(0, 1) });
	vertices.push_back({ glm::vec3(+1.5, 1, -1.5), glm::vec3(0, 1, 0), glm::vec2(1, 1) });
	//bottom								 
	vertices.push_back({ glm::vec3(-1.5, 0, -1.5), glm::vec3(0, -1, 0), glm::vec2(0, 0) });
	vertices.push_back({ glm::vec3(+1.5, 0, -1.5), glm::vec3(0, -1, 0), glm::vec2(1, 0) });
	vertices.push_back({ glm::vec3(-1.5, 0, +1.5), glm::vec3(0, -1, 0), glm::vec2(0, 1) });
	vertices.push_back({ glm::vec3(+1.5, 0, +1.5), glm::vec3(0, -1, 0), glm::vec2(1, 1) });

	std::vector<int> indices(36);
	int index = 0;
	for (int i = 0; i < 6 * 4; i += 4)
	{
		indices[index + 0] = i + 0;
		indices[index + 1] = i + 1;
		indices[index + 2] = i + 2;
		indices[index + 3] = i + 1;
		indices[index + 4] = i + 3;
		indices[index + 5] = i + 2;
		index += 6;
	}

	baseLegoVertexBuffer.BufferData(vertices);
	baseLegoIndices.BufferData(indices);
	baseLegoVAO.Init(
		{
			{ CreateAttribute<		0,
									glm::vec3,
									0,
									sizeof(Vertex)
								>, baseLegoVertexBuffer },
			{ CreateAttribute<1, glm::vec3, (sizeof(glm::vec3)), sizeof(Vertex)>, baseLegoVertexBuffer },
			{ CreateAttribute<2, glm::vec2, (2 * sizeof(glm::vec3)), sizeof(Vertex)>, baseLegoVertexBuffer },
		},
		baseLegoIndices
	);
}

void CMyApp::initSkyBox()
{
	skyboxPos.BufferData(
		std::vector<glm::vec3>{
		// hátsó lap
		glm::vec3(-1, -1, -1),
			glm::vec3(1, -1, -1),
			glm::vec3(1, 1, -1),
			glm::vec3(-1, 1, -1),
			// elülső lap
			glm::vec3(-1, -1, 1),
			glm::vec3(1, -1, 1),
			glm::vec3(1, 1, 1),
			glm::vec3(-1, 1, 1),
	}
	);

	skyboxIndices.BufferData(
		std::vector<int>{
		// hátsó lap
		0, 1, 2,
			2, 3, 0,
			// elülső lap
			4, 6, 5,
			6, 4, 7,
			// bal
			0, 3, 4,
			4, 3, 7,
			// jobb
			1, 5, 2,
			5, 6, 2,
			// alsó
			1, 0, 4,
			1, 4, 5,
			// felső
			3, 2, 6,
			3, 6, 7,
	}
	);

	skyboxVao.Init(
		{
			{ CreateAttribute<0, glm::vec3, 0, sizeof(glm::vec3)>, skyboxPos },
		}, skyboxIndices
		);

	glEnable(GL_TEXTURE_CUBE_MAP_SEAMLESS);

	skyboxTexture.AttachFromFile("assets/xpos.png", false, GL_TEXTURE_CUBE_MAP_POSITIVE_X);
	skyboxTexture.AttachFromFile("assets/xneg.png", false, GL_TEXTURE_CUBE_MAP_NEGATIVE_X);
	skyboxTexture.AttachFromFile("assets/ypos.png", false, GL_TEXTURE_CUBE_MAP_POSITIVE_Y);
	skyboxTexture.AttachFromFile("assets/yneg.png", false, GL_TEXTURE_CUBE_MAP_NEGATIVE_Y);
	skyboxTexture.AttachFromFile("assets/zpos.png", false, GL_TEXTURE_CUBE_MAP_POSITIVE_Z);
	skyboxTexture.AttachFromFile("assets/zneg.png", true, GL_TEXTURE_CUBE_MAP_NEGATIVE_Z);

	glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
	glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
	glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_WRAP_R, GL_CLAMP_TO_EDGE);

	glBindTexture(GL_TEXTURE_CUBE_MAP, 0);
}

void CMyApp::initShaders()
{
	legoShader.AttachShaders({
		{ GL_VERTEX_SHADER, "myVert.vert"},
		{ GL_FRAGMENT_SHADER, "myFrag.frag"}
		});

	legoShader.BindAttribLocations({
		{ 0, "vs_in_pos" },
		{ 1, "vs_in_norm" },
		{ 2, "vs_in_tex" },
		});

	legoShader.LinkProgram();


	trackShader.Init(
		{
			{ GL_VERTEX_SHADER, "trackVert.vert" },
			{ GL_FRAGMENT_SHADER, "trackFrag.frag" }
		},
		{
			{ 0, "vs_in_pos" },
			{ 1, "vs_in_norm" },
			{ 2, "vs_in_tex" }
		}
		);

	skyboxShader.Init(
		{
			{ GL_VERTEX_SHADER, "skybox.vert" },
			{ GL_FRAGMENT_SHADER, "skybox.frag" }
		},
		{
			{ 0, "vs_in_pos" },
		}
		);

	pointShader.Init(
		{
			{ GL_VERTEX_SHADER, "pointVert.vert" },
			{ GL_FRAGMENT_SHADER, "pointFrag.frag" }
		}
	);
}

bool CMyApp::init()
{
	glClearColor(0.125f, 0.25f, 0.5f, 1.0f);

	glEnable(GL_CULL_FACE);
	glEnable(GL_DEPTH_TEST);

	initShaders();
	initTrack();
	initBaseLego();
	initTorus();
	initSphere();
	initSkyBox();
	initBasicCars();
	addBase();
	initRoutePoints();
	addStaticContent();


	cylinderModel = std::unique_ptr<Mesh>(ObjParser::parse("assets/henger.obj"));
	cylinderModel->initBuffers();

	trackTexture.FromFile("assets/track.png");


	camera.SetProj(glm::radians(60.0f), 2600.f / 1000.0f, 0.01f, 1000.0f);

	return true;
}


void CMyApp::cleanUp()
{
}

void CMyApp::initRoutePoints() {

}

void CMyApp::update()
{
	static Uint32 last_time = SDL_GetTicks();
	delta_time = (SDL_GetTicks() - last_time) / 1000.0f;

	camera.Update(delta_time);

	if (int(car_time) >= controlPoints.size()) {
		rotateCamera = true;
		blink = true;
		car_time = 0.0f;
		static_car_time = 0.f;
		move_speed = 0.0f;
	}

	if (!buildPhase && rotateCamera) {
		if (cameraMoveTime > 18) {
			rotateCamera = false;
			cameraMoveTime = 0.0;
			buildPhase = true;
			blink = false;
		}
		else {
			cameraMoveTime += delta_time;
		}
	}

	if (rotateCamera) {
		float t=SDL_GetTicks() / 3000.f;
		float camX = (cosf(t)*30+ legoCar.at(2).position.x);
		float camZ = (sinf(t)*30+ legoCar.at(2).position.z);
		camera.SetView(glm::vec3(camX, 50, camZ), legoCar.at(2).position, glm::vec3(0, 1, 0));
		
		
	}
	else
		if (!buildPhase) {
			glm::vec3 actualCamera = startCameraPos + eval(car_time, controlPoints);
			camera.SetView(glm::vec3(actualCamera.x + 60, actualCamera.y + 50, actualCamera.z), actualCamera, glm::vec3(0, 1, 0));
			car_time += (delta_time * move_speed);
			static_car_time += delta_time;
		}

	last_time = SDL_GetTicks();
}


void CMyApp::addStaticCarElements(std::vector<LegoBlock>& car, float tx, float tz) {
	auto body = LegoBlock{
		glm::vec4(0.5f, 0.5f, 0.5f, 1.f),
		glm::vec3(-12 + tx, 3, -51 + tz),
		true,
		false,
		false,
		6,4
	};
	car.push_back(body);

	auto driver = LegoBlock{
		glm::vec4(0.5f, 0.5f, 0.5f, 1.f),
		glm::vec3(-12 + tx, 4, -48 + tz),
		true,
		false,
		true,
		1,1
	};
	car.push_back(driver);


	auto spr1 = LegoBlock{
		glm::vec4(0.5f, 0.5f, 0.5f, 1.f),
		glm::vec3(0 + tx, 4, -48 + tz),
		false,
		false,
		false,
		2,2
	};
	car.push_back(spr1);

	auto spr2 = LegoBlock{
		glm::vec4(0.5f, 0.5f, 0.5f, 1.f),
		glm::vec3(0 + tx, 7, -48 + tz),
		false,
		false,
		false,
		2,2
	};
	car.push_back(spr2);

	auto spoiler = LegoBlock{
		glm::vec4(0.5f, 0.5f, 0.5f, 1.f),
		glm::vec3(0 + tx, 10, -51 + tz),
		true,
		false,
		false,
		2,4
	};
	car.push_back(spoiler);



	/*struct LegoBlock {
		glm::vec4 color = glm::vec4(0.3f, 0.3f, 0.9f, 1.f);
		glm::vec3 position = glm::vec3(0.f, 0.f, 0.f);
		bool slim = false;
		bool wheel = false;
		bool driver = false;
		int lengthElem = 1;
		int widthElem = 1;
	};*/
}


void CMyApp::addStaticContent() {
	for (auto position = treePositions.begin(); position != treePositions.end(); ++position) {

		for (int i = 0; i < 9; i += 3) {
			auto element = LegoBlock{
				glm::vec4(0.6f, 0.3f, 0.2f, 1.0f),
				glm::vec3(position->x, i, position->z),
				false,
				false,
				false,
				1,1
			};
			staticContent.push_back(element);
		}

		for (int i = 9; i < 21; i += 4) {
			auto leaf = LegoBlock{
				glm::vec4(0.1f, 0.5f, 0.1f, 1.f),
				glm::vec3(position->x - 3, i, position->z - 9),
				true,
				false,
				false,
				3,7
			};
			auto element = LegoBlock{
				glm::vec4(0.6f, 0.3f, 0.2f, 1.0f),
				glm::vec3(position->x, i + 1, position->z),
				false,
				false,
				false,
				1,1
			};
			staticContent.push_back(leaf);
			staticContent.push_back(element);
		}
	}

	for (auto position = pyramPositions.begin(); position != pyramPositions.end(); ++position) {
		int k = 0;
		for (int i = 15; i >= 0; i -= 2) {

			auto element = LegoBlock{
				glm::vec4(0.2f, 0.6f, 0.8f, 1.0f),
				glm::vec3(position->x + k, k, position->z + k),
				false,
				false,
				false,
				i,i
			};
			staticContent.push_back(element);
			k += 3;
		}
	}

}

void CMyApp::initBasicCars() {
	generateBase(staticCar1, 30.f, -12.f, true);
	addStaticCarElements(staticCar1, 30.f, -12.f);
}


void CMyApp::generateBase(std::vector<LegoBlock>& car, float tx, float tz, bool grey) {
	car.clear();

	auto frontLeftWheel = LegoBlock{};
	frontLeftWheel.wheel = true;
	frontLeftWheel.slim = true;
	frontLeftWheel.position = glm::vec3(1.5 + tx, 0, -37.5 + tz);
	frontLeftWheel.color = glm::vec4(glm::vec3(0.1f), 1.f);
	car.push_back(frontLeftWheel);

	auto frontRightWheel = LegoBlock{};
	frontRightWheel.wheel = true;
	frontRightWheel.slim = true;
	frontRightWheel.position = glm::vec3(1.5 + tx, 0, -55.5 + tz);
	frontRightWheel.color = glm::vec4(glm::vec3(0.1f), 1.f);
	car.push_back(frontRightWheel);

	auto frontBind = LegoBlock{};
	frontBind.position = glm::vec3(0 + tx, 2, -51 + tz);
	frontBind.color = glm::vec4(0.2, 0.2, 0.9, 1.0);
	frontBind.slim = true;
	frontBind.lengthElem = 2;
	frontBind.widthElem = 4;
	car.push_back(frontBind);


	auto rearLeftWheel = LegoBlock{};
	rearLeftWheel.wheel = true;
	rearLeftWheel.slim = true;
	rearLeftWheel.position = glm::vec3(-10.5 + tx, 0, -37.5 + tz);
	rearLeftWheel.color = glm::vec4(glm::vec3(0.1f), 1.f);
	car.push_back(rearLeftWheel);

	auto rearRightWheel = LegoBlock{};
	rearRightWheel.wheel = true;
	rearRightWheel.slim = true;
	rearRightWheel.position = glm::vec3(-10.5 + tx, 0, -55.5 + tz);
	rearRightWheel.color = glm::vec4(glm::vec3(0.1f), 1.f);
	car.push_back(rearRightWheel);

	auto rearBind = LegoBlock{};
	rearBind.position = glm::vec3(-12 + tx, 2, -51 + tz);
	rearBind.color = glm::vec4(0.2, 0.2, 0.9, 1.0);
	rearBind.slim = true;
	rearBind.lengthElem = 2;
	rearBind.widthElem = 4;
	car.push_back(rearBind);

	if (grey) {
		for (auto item = car.begin(); item != car.end(); ++item) {
			item->color = glm::vec4(0.5, 0.5, 0.5, 1.0);
		}
	}
}

void CMyApp::addBase() {
	generateBase(legoCar, 0.f, 0.f, false);
}

void CMyApp::drawTrack(glm::mat4 viewProj) {
	glm::vec3 pl = glm::vec3(0, 0, 0);
	if (pointLight) {
		pl = glm::vec3(1, 0, 0);
	}
	if (blink) {
		int cm = ((int)cameraMoveTime) % 2;
		pl.z = 1;
		pl.y = cm;
	}


	trackShader.Use();
	trackVAO.Bind();
	trackShader.SetUniform("eyePos", camera.GetEye());
	trackShader.SetUniform("isPointLight", pl);

	trackShader.SetUniform("spotPosition", carPos);
	trackShader.SetUniform("spotDirection", carDirection);
	trackShader.SetTexture("texImage", 0, trackTexture);

	glm::mat4 trackWorld = glm::mat4(1);

	trackShader.SetUniform("MVP", viewProj * trackWorld);
	trackShader.SetUniform("world", trackWorld);
	trackShader.SetUniform("worldIT", glm::inverse(glm::transpose(trackWorld)));
	glDrawElements(GL_TRIANGLES, 3 * 2 * TRACK_N * TRACK_N, GL_UNSIGNED_SHORT, 0);

	trackShader.Unuse();

}

void  CMyApp::drawDriver(LegoBlock block, glm::mat4 viewProj, glm::mat4  baseDriverWorld, float xPos, float zPos, bool canmove, float rotate) {
	auto rota = glm::mat4(1);

	if (canmove) {
		rota *= glm::translate(glm::vec3(xPos - 7.5, 0, 4.5 + zPos + 37))
			* glm::rotate(rotate, glm::vec3(0.f, 1.f, 0.f))
			* glm::translate(glm::vec3((-xPos) + 7.5, 0, (-zPos) - 4.5 - 37))
			;
	}

	//Wheel
	glm::mat4 wheelWorld = rota * baseDriverWorld
		* glm::translate(glm::vec3(-1.5, 4.5, 1.5))
		* glm::rotate((float)M_PI / 2.f, glm::vec3(0.f, 0.f, 1.f))
		* glm::scale<float>(glm::vec3(.3, 0.2, .3));
	legoShader.SetUniform("MVP", viewProj * wheelWorld);
	legoShader.SetUniform("world", wheelWorld);
	legoShader.SetUniform("worldIT", glm::inverse(glm::transpose(wheelWorld)));
	torusVAO.Bind();
	glDrawElements(GL_TRIANGLES, 3 * 2 * N * M, GL_UNSIGNED_SHORT, 0);

	//base
	glm::mat4 baseWorld1 = rota * baseDriverWorld;
	legoShader.SetUniform("MVP", viewProj * baseWorld1);
	legoShader.SetUniform("world", baseWorld1);
	legoShader.SetUniform("worldIT", glm::inverse(glm::transpose(baseWorld1)));
	baseLegoVAO.Bind();
	glDrawElements(GL_TRIANGLES, 36, GL_UNSIGNED_INT, nullptr);

	glm::mat4 baseWorld2 = rota * baseDriverWorld * glm::translate(glm::vec3(3, 0, 0));
	legoShader.SetUniform("MVP", viewProj * baseWorld2);
	legoShader.SetUniform("world", baseWorld2);
	legoShader.SetUniform("worldIT", glm::inverse(glm::transpose(baseWorld2)));
	baseLegoVAO.Bind();
	glDrawElements(GL_TRIANGLES, 36, GL_UNSIGNED_INT, nullptr);

	glm::mat4 baseWorld3 = rota * baseDriverWorld * glm::translate(glm::vec3(3, 0, 3));
	legoShader.SetUniform("MVP", viewProj * baseWorld3);
	legoShader.SetUniform("world", baseWorld3);
	legoShader.SetUniform("worldIT", glm::inverse(glm::transpose(baseWorld3)));
	baseLegoVAO.Bind();
	glDrawElements(GL_TRIANGLES, 36, GL_UNSIGNED_INT, nullptr);

	glm::mat4 baseWorld4 = rota * baseDriverWorld * glm::translate(glm::vec3(0, 0, 3));
	legoShader.SetUniform("MVP", viewProj * baseWorld4);
	legoShader.SetUniform("world", baseWorld4);
	legoShader.SetUniform("worldIT", glm::inverse(glm::transpose(baseWorld4)));
	baseLegoVAO.Bind();
	glDrawElements(GL_TRIANGLES, 36, GL_UNSIGNED_INT, nullptr);

	//body

	for (int i = 1; i < 6; i++) {
		glm::mat4 bodyWorld = rota * baseDriverWorld * glm::translate(glm::vec3(3, i, 1.5));
		legoShader.SetUniform("MVP", viewProj * bodyWorld);
		legoShader.SetUniform("world", bodyWorld);
		legoShader.SetUniform("worldIT", glm::inverse(glm::transpose(bodyWorld)));
		baseLegoVAO.Bind();
		glDrawElements(GL_TRIANGLES, 36, GL_UNSIGNED_INT, nullptr);
	}

	//head
	glm::mat4 sphereWorld = rota * baseDriverWorld
		* glm::translate(glm::vec3(3, 7.5, 1.5))

		;
	legoShader.SetUniform("MVP", viewProj * sphereWorld);
	legoShader.SetUniform("world", sphereWorld);
	legoShader.SetUniform("worldIT", glm::inverse(glm::transpose(sphereWorld)));
	sphereVAO.Bind();
	glDrawElements(GL_TRIANGLES, 3 * 2 * N * M, GL_UNSIGNED_SHORT, 0);


	//rod
	glm::mat4  cylindereWorld = rota * baseDriverWorld
		* glm::translate(glm::vec3(-0.8, 1.9, 1.5))
		* glm::rotate((float)M_PI / 5.f, glm::vec3(0.f, 0.f, 1.f))
		* glm::scale<float>(glm::vec3(1.3, 0.65, 1.3))

		;

	legoShader.SetUniform("MVP", viewProj * cylindereWorld);
	legoShader.SetUniform("world", cylindereWorld);
	legoShader.SetUniform("worldIT", glm::inverse(glm::transpose(cylindereWorld)));

	cylinderModel->draw();


}

void CMyApp::drawLegoElement(LegoBlock block, glm::mat4 viewProj, glm::mat4  basePosition, float xPos, float zPos, bool canmove, float rotate) {

	auto rota = glm::mat4(1);

	if (canmove) {
		rota *= glm::translate(glm::vec3(xPos - 7.5, 0, 4.5 + zPos + 37))
			* glm::rotate(rotate, glm::vec3(0.f, 1.f, 0.f))
			* glm::translate(glm::vec3((-xPos) + 7.5, 0, (-zPos) - 4.5 - 37))
			;
	}

	glm::mat4  cubeWorld = rota * basePosition
		;

	legoShader.SetUniform("MVP", viewProj * cubeWorld);
	legoShader.SetUniform("world", cubeWorld);
	legoShader.SetUniform("worldIT", glm::inverse(glm::transpose(cubeWorld)));
	baseLegoVAO.Bind();
	glDrawElements(GL_TRIANGLES, 36, GL_UNSIGNED_INT, nullptr);


	glm::mat4  cylindereWorld = rota * basePosition
		* glm::translate(glm::vec3(0, 1.2f, 0))
		* glm::scale<float>(glm::vec3(2.5, 0.1, 2.5))
		;

	legoShader.SetUniform("MVP", viewProj * cylindereWorld);
	legoShader.SetUniform("world", cylindereWorld);
	legoShader.SetUniform("worldIT", glm::inverse(glm::transpose(cylindereWorld)));

	cylinderModel->draw();
}

void CMyApp::drawWheel(LegoBlock block, glm::mat4 viewProj, glm::mat4  basePosition, float xPos, float zPos, bool canmove, float rotate) {

	auto rota = glm::mat4(1);

	if (canmove) {
		rota *= glm::translate(glm::vec3(xPos - 7.5, 0, 4.5 + zPos + 37))
			* glm::rotate(rotate, glm::vec3(0.f, 1.f, 0.f))
			* glm::translate(glm::vec3((-xPos) + 7.5, 0, (-zPos) - 4.5 - 37))
			;
	}

	glm::mat4  cylindereWorld = glm::mat4(1)

		* glm::translate(glm::vec3(0, 3.f, 0))

		* rota
		* basePosition
		* glm::scale<float>(glm::vec3(10.f, 10.f, 1.5f))
		* glm::rotate((float)M_PI / 2.f, glm::vec3(1.f, 0.f, 0.f))
		;

	legoShader.SetUniform("MVP", viewProj * cylindereWorld);
	legoShader.SetUniform("world", cylindereWorld);
	legoShader.SetUniform("worldIT", glm::inverse(glm::transpose(cylindereWorld)));

	cylinderModel->draw();
}


void CMyApp::drawLego(glm::mat4 viewProj, LegoBlock block, bool canmove, float xPos, float zPos, std::vector<glm::vec3> controlPointVec, float time,bool mainCar) {


	legoShader.Use();
	legoShader.SetUniform("eyePos", camera.GetEye());

	glm::vec3 pl = glm::vec3(0, 0, 0);
	if (pointLight) {
		pl = glm::vec3(1, 0, 0);
	}
	if (blink) {
		int cm = ((int)cameraMoveTime)%2;
		pl.z = 1;
		pl.y = cm;
	}
	legoShader.SetUniform("isPointLight", pl);


	float angle = 0.0f;
	if (!buildPhase && canmove) {
		auto e = eval(time, controlPointVec);
		
		xPos += e.x;
		zPos += e.z;
		block.position += glm::vec3(e.x, 0, e.z);
		auto muled = getIntervalIndex(time, controlPointVec);
		auto currentPoint = glm::vec3(muled.first);
		currentPoint.z *= -1;
		auto nextPoint = glm::vec3(muled.second);
		nextPoint.z *= -1;
		auto v = currentPoint - nextPoint;
		angle = std::atan2f(v.z, v.x);

		if (mainCar) {
			glm::vec3 ps = legoCar.at(2).position;
			carPos = glm::vec3(ps.x + e.x - 15, ps.y, ps.z + e.z + 42);

			carDirection = (  muled.second- muled.first);
		}

	}


	for (int row = 0; row < block.lengthElem; row++) {
		for (int col = 0; col < block.widthElem; ++col) {
			int height = 3;
			if (block.slim) {
				height = 1;
			}
			for (int hgt = 0; hgt < height; ++hgt) {

				legoShader.SetUniform("color", block.color);
				glm::mat4  basePosition = glm::mat4(1);

				basePosition = glm::mat4(1)
					* glm::translate(block.position)
					* glm::translate(glm::vec3(3 * row, hgt, 3 * col))
					;

				if (!buildPhase && canmove) {
					basePosition *= glm::translate(glm::vec3(0, 0, 37));
				}


				if (block.wheel) {
					drawWheel(block, viewProj, basePosition, xPos, zPos, canmove, angle);

				}
				else if (block.driver) {
					drawDriver(block, viewProj, basePosition, xPos, zPos, canmove, angle);
				}
				else {
					drawLegoElement(block, viewProj, basePosition, xPos, zPos, canmove, angle);
				}
			}
		}
	}



	legoShader.Unuse();

}

void CMyApp::drawLegos(glm::mat4 viewProj) {
	float currentMillis = SDL_GetTicks();
	float currentDelta = currentMillis - lastMillis;
	float speed = currentDelta / 10;
	lastMillis = currentMillis;

	if (showstatic) {
		for (auto item = staticContent.begin(); item != staticContent.end(); ++item) {
			drawLego(viewProj, *item, false, 0, 0, noCp, 0,false);
		}
	}
	for (auto item = legoCar.begin(); item != legoCar.end(); ++item) {
		glm::vec3 ps = legoCar.at(2).position;
		carPos = glm::vec3(ps.x-15 , ps.y, ps.z );
		carDirection = glm::vec3(-10, 0, 0);
		drawLego(viewProj, *item, true, legoCar.at(2).position.x, legoCar.at(2).position.z, controlPoints, car_time,true);
	}

	for (auto item = staticCar1.begin(); item != staticCar1.end(); ++item) {
		drawLego(viewProj, *item, true, staticCar1.at(2).position.x, staticCar1.at(2).position.z, controlPointsSC1, static_car_time,false);
	}

	if (buildPhase == true) {
		drawLego(viewProj, actualElement, false, 0, 0, noCp, 0,false);
	}
}

void  CMyApp::drawControllPoints() {
	pointShader.Use();

	pointShader.SetUniform("mvp", camera.GetViewProj());
	pointShader.SetUniform("points", controlPoints);
	pointShader.SetUniform("color", glm::vec4(1, 0, 1, 1));

	glDrawArrays(GL_POINTS, 0, (GLsizei)controlPoints.size());

	pointShader.SetUniform("color", glm::vec4(1, 1, 0.1, 1));
	glDrawArrays(GL_LINE_STRIP, 0, (GLsizei)controlPoints.size());
}

void CMyApp::render()
{
	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

	glm::mat4 viewProj = camera.GetViewProj();

	drawLegos(viewProj);
	drawTrack(viewProj);
	//drawControllPoints();


	GLint prevDepthFnc;
	glGetIntegerv(GL_DEPTH_FUNC, &prevDepthFnc);

	glDepthFunc(GL_LEQUAL);

	/*skyboxVao.Bind();
	skyboxShader.Use();
	skyboxShader.SetUniform("MVP", viewProj * glm::translate(camera.GetEye()));

	glActiveTexture(GL_TEXTURE0);
	glBindTexture(GL_TEXTURE_CUBE_MAP, skyboxTexture);
	glUniform1i(skyboxShader.GetLocation("skyboxTexture"), 0);

	glDrawElements(GL_TRIANGLES, 36, GL_UNSIGNED_INT, nullptr);
	skyboxShader.Unuse();*/

	glDepthFunc(prevDepthFnc);



	if (ImGui::Begin("Game GUI")) {
		ImGui::Checkbox("Night", &pointLight);
		if (ImGui::Button("Build new")) {
				buildPhase = true;
				car_time = 0.f;
				static_car_time = 0.f;
				move_speed = 0.0f;
				addBase();
				initBasicCars();
			}
		if (ImGui::Button("Restart ")) {
			car_time = 0.f;
			static_car_time = 0.f;
			move_speed = 0.0f;
			initBasicCars();

		}
		ImGui::Checkbox("Show buildings", &showstatic);
		if (buildPhase) {
			ImGui::Checkbox("Driver", &actualElement.driver);

			if (actualElement.driver == false) {
				ImGui::SliderInt("Lego length", &actualElement.lengthElem, 1, 20);
				ImGui::SliderInt("Lego width", &actualElement.widthElem, 1, 20);
				ImGui::Checkbox("Slim", &actualElement.slim);
			}
			else {
				actualElement.widthElem = 1;
				actualElement.lengthElem = 1;
				actualElement.slim = true;
			}
			ImGui::ColorEdit4("Color", actualCol);
			if (actualElement.driver == false) {
				if (ImGui::Button("Rotate 90")) {
					int tmp = actualElement.lengthElem;
					actualElement.lengthElem = actualElement.widthElem;
					actualElement.widthElem = tmp;

				}
			}

		}
		
		//if (ImGui::Button("Rotate car")) {
			//rotation = rotation + ((float)M_PI / 2);
		//}
	}
	ImGui::End();
	actualElement.color = glm::vec4(actualCol[0], actualCol[1], actualCol[2], actualCol[3]);
}

void CMyApp::buildElementIn() {
	if (isOnElement()) {
		legoCar.push_back(actualElement);
		actualElement = LegoBlock{};
		actualCol[0] = 0.3;
		actualCol[1] = 0.3;
		actualCol[2] = 0.9;
		actualCol[3] = 1.0;
	}

}

bool CMyApp::isOnElement() {

	for (auto item = legoCar.begin(); item != legoCar.end(); ++item) {

		if (item->driver || item->wheel) {

		}
		else {
			for (int row = 0; row < item->lengthElem; row++) {
				for (int col = 0; col < item->widthElem; ++col) {
					int height = 2;
					if (item->slim) {
						height = 0;
					}
					glm::vec3 itemPos = item->position + glm::vec3(3 * row, height, 3 * col);

					for (int arow = 0; arow < actualElement.lengthElem; arow++) {
						for (int acol = 0; acol < actualElement.widthElem; ++acol) {
							glm::vec3 actualPos = actualElement.position + glm::vec3(3 * arow, 0, 3 * acol);
							if (
								actualPos.x == itemPos.x && actualPos.z == itemPos.z
								&& actualPos.y == (itemPos.y + 1)
								) {
								return true;
							}
						}

					}

				}

			}

		}
	}


	return false;
}

bool CMyApp::canMoveTo(glm::vec3 newPos) {
	if (newPos.y < 0) {
		return false;
	}



	for (auto item = legoCar.begin(); item != legoCar.end(); ++item) {

		if (item->driver) {

		}
		else {
			for (int row = 0; row < item->lengthElem; row++) {
				for (int col = 0; col < item->widthElem; ++col) {
					int height = 3;
					if (!item->wheel && item->slim) {
						height = 1;
					}
					for (int hgt = 0; hgt < height; ++hgt) {
						glm::vec3 itemPos = item->position + glm::vec3(3 * row, hgt, 3 * col);

						for (int arow = 0; arow < actualElement.lengthElem; arow++) {
							for (int acol = 0; acol < actualElement.widthElem; ++acol) {

								int aheight = 3;
								if (actualElement.slim) {
									aheight = 1;
								}

								for (int ahgt = 0; ahgt < aheight; ++ahgt) {
									glm::vec3 actualPos = newPos + glm::vec3(3 * arow, ahgt, 3 * acol);
									//if (item->wheel) {
										//std::cout << "WHEEL" << std::endl;
									//}
									//std::cout << actualPos.x << ", " << actualPos.y << ", " << actualPos.z << " ||| " << itemPos.x << ", " << itemPos.y << ", " << itemPos.z << std::endl;

									if (
										actualPos.x == itemPos.x && actualPos.z == itemPos.z
										&& actualPos.y == itemPos.y
										) {

										return false;
									}
								}
							}

						}
					}

				}

			}

		}
		//std::cout << "------------------" << std::endl;
	}






	//TODO összes eleme benne van-e másik elemben
	return true;
}

void CMyApp::setColor(float r, float g, float b) {
	actualCol[0] = r;
	actualCol[1] = g;
	actualCol[2] = b;
}

void CMyApp::moveActual(SDL_KeyboardEvent& key) {

	if (buildPhase == true) {

		if (key.keysym.sym == SDLK_4 || key.keysym.sym == SDLK_KP_4) {
			glm::vec3 newPos = actualElement.position;
			newPos.z += 3;
			if (canMoveTo(newPos)) {
				actualElement.position.z += 3;
			}
		}
		else if (key.keysym.sym == SDLK_6 || key.keysym.sym == SDLK_KP_6) {
			glm::vec3 newPos = actualElement.position;
			newPos.z -= 3;
			if (canMoveTo(newPos)) {
				actualElement.position.z -= 3;
			}
		}
		else if (key.keysym.sym == SDLK_8 || key.keysym.sym == SDLK_KP_8) {
			glm::vec3 newPos = actualElement.position;
			newPos.x -= 3;
			if (canMoveTo(newPos)) {
				actualElement.position.x -= 3;
			}
		}
		else if (key.keysym.sym == SDLK_2 || key.keysym.sym == SDLK_KP_2) {
			glm::vec3 newPos = actualElement.position;
			newPos.x += 3;
			if (canMoveTo(newPos)) {
				actualElement.position.x += 3;
			}
		}
		else if (key.keysym.sym == SDLK_3 || key.keysym.sym == SDLK_KP_3) {
			glm::vec3 newPos = actualElement.position;
			newPos.y -= 1;
			if (canMoveTo(newPos)) {
				actualElement.position.y -= 1;
			}
		}
		else if (key.keysym.sym == SDLK_9 || key.keysym.sym == SDLK_KP_9) {
			glm::vec3 newPos = actualElement.position;
			newPos.y += 1;
			if (canMoveTo(newPos)) {
				actualElement.position.y += 1;
			}
		}
		else if (key.keysym.sym == SDLK_5 || key.keysym.sym == SDLK_KP_5) {
			int tmp = actualElement.lengthElem;
			actualElement.lengthElem = actualElement.widthElem;
			actualElement.widthElem = tmp;
		}
		else if (key.keysym.sym == SDLK_0 || key.keysym.sym == SDLK_KP_0) {
			buildElementIn();
		}
		else if (key.keysym.sym == SDLK_1 || key.keysym.sym == SDLK_KP_1) {
			auto lastElement = legoCar.back();
			glm::vec3 pos = lastElement.position;
			//std::cout << pos.y << std::endl;
			if (lastElement.slim) {
				pos.y += 1;
			}
			else {
				pos.y += 3;
			}

			actualElement.position = pos;
			buildElementIn();
		}
		else if (key.keysym.sym == SDLK_t) { //red
			setColor(1, 0, 0);
		}
		else if (key.keysym.sym == SDLK_z) { //yellow
			setColor(1, 1, 0);
		}
		else if (key.keysym.sym == SDLK_u) { // green
			setColor(0, 1, 0);
		}
		else if (key.keysym.sym == SDLK_i) { //blue
			setColor(0, 0, 1);
		}
		else if (key.keysym.sym == SDLK_o) { //white
			setColor(.9, .9, .9);
		}
		else if (key.keysym.sym == SDLK_p) { //black
			setColor(0.1, 0.1, 0.1);
		}
		else if (key.keysym.sym == SDLK_KP_ENTER || key.keysym.sym == SDLK_RETURN) {
			if (isBuildCorrect()) {
				buildPhase = false;
				camMovePhase = true;
				startCameraPos = camera.GetEye();
			}

		}
	}
	else {
		if (key.keysym.sym == SDLK_UP) {
			move_speed += 0.3;
			if (move_speed > 3.0) {
				move_speed = 3.0;
			}
		}
		else {
			if (key.keysym.sym == SDLK_DOWN) {
				move_speed -= 0.3;
				if (move_speed < 0.0) {
					move_speed = 0.0;
				}
			}
		}
	}
	//TODO control car


}

bool CMyApp::isBuildCorrect() {
	bool result = false;
	for (auto item = legoCar.begin(); item != legoCar.end(); ++item) {
		if (item->driver) {
			result = true;
		}
	}
	return result;
}

void CMyApp::keyboardDown(SDL_KeyboardEvent& key)
{
	if (buildPhase) {
		camera.KeyboardDown(key);
	}
	moveActual(key);
}

void CMyApp::keyboardUp(SDL_KeyboardEvent& key)
{
	if (buildPhase) {
		camera.KeyboardUp(key);
	}
}

void CMyApp::mouseMove(SDL_MouseMotionEvent& mouse)
{
	if (buildPhase) {
		camera.MouseMove(mouse);
	}
}

void CMyApp::mouseDown(SDL_MouseButtonEvent& mouse)
{
}

void CMyApp::mouseUp(SDL_MouseButtonEvent& mouse)
{
}

void CMyApp::mouseWheel(SDL_MouseWheelEvent& wheel)
{
}

// a két paraméterbe az új ablakméret szélessége (_w) és magassága (_h) található
void CMyApp::resizeWindow(int _w, int _h)
{
	glViewport(0, 0, _w, _h);

	camera.Resize(_w, _h);
}


glm::vec3 CMyApp::eval(float t, std::vector<glm::vec3> controlPointVec)
{

	if (controlPointVec.size() == 0)
		return glm::vec3(0);

	int interval = (int)t;

	if (interval < 0)
		return controlPointVec[0];

	if (interval >= controlPointVec.size() - 1)//TODO end
		return controlPointVec[controlPointVec.size() - 1];

	float localT = t - interval;

	if (controlPointVec.size() < 4 || interval == 0 || interval >= controlPointVec.size() - 2) {
		return (1 - localT) * controlPointVec[interval] + localT * controlPointVec[interval + 1];
	}

	return catmull(localT, controlPointVec[interval - 1], controlPointVec[interval], controlPointVec[interval + 1], controlPointVec[interval + 2]);


}

glm::vec3 CMyApp::catmull(float t, glm::vec3 p0, glm::vec3 p1, glm::vec3 p2, glm::vec3 p3) {
	return 0.5f * (
		(2.f * p1) +
		(-p0 + p2) * t +
		(2.f * p0 - 5.f * p1 + 4.f * p2 - p3) * t * t +
		(-p0 + 3.f * p1 - 3.f * p2 + p3) * t * t * t
		);
}



Muled CMyApp::getIntervalIndex(float t, std::vector<glm::vec3> controlPointVec) {

	int interval = (int)t;

	if (interval <= 0) {

		return Muled{
			controlPointVec[0],controlPointVec[1]
		};
	}

	if (interval >= controlPointVec.size() - 1) {
		return Muled{
			controlPointVec[controlPointVec.size() - 2],controlPointVec[controlPointVec.size() - 1]
		};
	}

	if (interval >= controlPointVec.size() - 3) {
		return Muled{
			controlPointVec[interval],controlPointVec[interval + 1]
		};
	}
	float localT = t - interval;


	return Muled{
		catmull(localT, controlPointVec[interval - 1], controlPointVec[interval], controlPointVec[interval + 1], controlPointVec[interval + 2]),
		catmull(localT, controlPointVec[interval], controlPointVec[interval + 1], controlPointVec[interval + 2], controlPointVec[interval + 3])
	};
}
