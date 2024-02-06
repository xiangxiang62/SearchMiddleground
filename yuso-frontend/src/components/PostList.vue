<script setup lang="ts">
import { withDefaults, defineProps } from "vue";

interface Props {
  postList: any[];
}

const props = withDefaults(defineProps<Props>(), {
  postList: () => [],
});
</script>

<template>
  <a-list item-layout="horizontal" :data-source="props.postList">
    <template #renderItem="{ item }">
      <a-list-item>
        <a-list-item-meta :description="item.content">
          <template #title>
            {{ item.title }}
          </template>
          <template #avatar>
            <a-avatar
              src="https://img2.baidu.com/it/u=3385181023,3772644235&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500"
            />
          </template>
        </a-list-item-meta>
      </a-list-item>
    </template>
  </a-list>
</template>
<style scoped></style>
