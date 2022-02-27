#version 140

// pipeline-ból bejövő per-fragment attribútumok
in vec3 vs_out_pos;
in vec2 vs_out_tex0;

// kimenő érték - a fragment színe
out vec4 fs_out_col;

uniform sampler2D texImage;

void main()
{
	fs_out_col = texture(texImage, vs_out_tex0);
}